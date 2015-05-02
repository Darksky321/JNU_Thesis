package com.jnu.thesis.util.download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

public class DownloadTask extends Thread {
	private DownloadInfoBean siteInfoBean = null; // 文件信息 Bean
	private long[] nStartPos; // 开始位置
	private long[] nEndPos; // 结束位置
	private FileSplitterFetch[] fileSplitterFetch; // 子线程对象
	private long nFileLength; // 文件长度
	private long nBytesSaved; // 已保存字节数
	private long nBytesRead; // 已读字节数
	private long nBytes; // 已保存加已读
	private boolean bFirst = true; // 是否第一次取文件
	private boolean bStop = false; // 停止标志
	private File tmpFile; // 文件下载的临时信息
	private DataOutputStream output; // 输出到文件的输出流
	private List<DownloadEventListener> listeners = new ArrayList<DownloadEventListener>();
	private Context context;

	public DownloadTask(DownloadInfoBean bean, Context context)
			throws IOException { // 初始化下载信息,如果要进行断点续传,则初始化下载起始位置
		this.context = context;
		siteInfoBean = bean;
		// tmpFile = File.createTempFile ("zhong","1111",new
		// File(bean.getSFilePath()));
		tmpFile = new File(bean.getsFilePath() + File.separator
				+ bean.getsFileName() + ".info");
		nBytesSaved = 0;
		if (tmpFile.exists()) {
			System.out.println("tmp file exists");
			bFirst = false;
			read_nPos();
			for (int i = 0; i < nStartPos.length; i++) {
				nBytesSaved += nStartPos[i] - i
						* (nFileLength / nStartPos.length);
			}
		} else {
			System.out.println("tmp file not exists");
			nStartPos = new long[bean.getnSplitter()];
			nEndPos = new long[bean.getnSplitter()];
		}
	}

	public void run() {
		// 获得文件长度
		// 分割文件
		// 实例 FileSplitterFetch
		// 启动 FileSplitterFetch 线程
		// 等待子线程返回
		try {
			if (bFirst) { // 如果是第一次下载,则根据文件长度初始化下载起始结束位置
				nFileLength = getFileSize();
				if (nFileLength == -1) {
					System.out.println("File Length is not known!");
				} else if (nFileLength == -2) {
					System.out.println("File is not access!");
				} else {
					for (int i = 0; i < nStartPos.length; i++) {
						nStartPos[i] = (long) (i * (nFileLength / nStartPos.length));
					}
					for (int i = 0; i < nEndPos.length - 1; i++) {
						nEndPos[i] = nStartPos[i + 1] - 1;
					}
					nEndPos[nEndPos.length - 1] = nFileLength;
				}
			}
			// 启动子线程
			fileSplitterFetch = new FileSplitterFetch[nStartPos.length];
			for (int i = 0; i < nStartPos.length; i++) {
				fileSplitterFetch[i] = new FileSplitterFetch(
						siteInfoBean.getsSiteURL(), siteInfoBean.getsFilePath()
								+ File.separator + siteInfoBean.getsFileName(),
						nStartPos[i], nEndPos[i], i);
				Utility.log("Thread " + i + " , nStartPos = " + nStartPos[i]
						+ ", nEndPos = " + nEndPos[i]);
				fileSplitterFetch[i].start();
			}
			// fileSplitterFetch[nPos.length-1] = new
			// FileSplitterFetch(siteInfoBean.getSSiteURL(),
			// siteInfoBean.getSFilePath() + File.separator +
			// siteInfoBean.getsFileName(),nPos[nPos.length-1],nFileLength,nPos.length-1);
			// Utility.log("Thread " +(nPos.length-1) +
			// ",nStartPos = "+nPos[nPos.length-1]+", nEndPos = " +
			// nFileLength);
			// fileSplitterFetch[nPos.length-1].start();
			// 等待子线程结束
			// int count = 0;
			// 是否结束 while 循环
			boolean breakWhile = false;
			while (!bStop) {
				write_nPos();
				Utility.sleep(500);
				breakWhile = true;
				nBytesRead = 0;
				for (int i = 0; i < nStartPos.length; i++) {
					nBytesRead += fileSplitterFetch[i].bytesRead;
					if (!fileSplitterFetch[i].bDownOver) {
						breakWhile = false;
						break;
					}
				}
				System.out.println(nBytesRead);
				nBytes = nBytesRead + nBytesSaved;
				System.out.println(nBytes + "/" + nFileLength);
				if (breakWhile)
					break;
				// count++;
				// if(count>4)
				// siteStop();
			}
			if (nBytes >= nFileLength) {
				System.out.println("文件下载结束！");
				tmpFile.delete();
				Intent intent = new Intent();
				intent.setAction("com.jnu.thesis.DOWNLOAD_FINISH");
				context.sendBroadcast(intent);
			} else {
				System.out.println("文件下载未完成！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.toString());
		}
	} // 获得文件长度

	public long getFileSize() {
		int nFileLength = -1;
		try {
			URL url = new URL(siteInfoBean.getsSiteURL());
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				processErrorCode(responseCode);
				return -2; // -2 represent access is error
			}
			String sHeader;
			for (int i = 1;; i++) {
				// DataInputStream in = new
				// DataInputStream(httpConnection.getInputStream ());
				// Utility.log(in.readLine());
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utility.log(nFileLength);
		return nFileLength;
	}

	// 保存下载信息（文件指针位置）
	private void write_nPos() {
		try {
			output = new DataOutputStream(new FileOutputStream(tmpFile));
			output.writeInt(nStartPos.length);
			for (int i = 0; i < nStartPos.length; i++) {
				// output.writeLong(nPos[i]);
				output.writeLong(fileSplitterFetch[i].nStartPos);
				output.writeLong(fileSplitterFetch[i].nEndPos);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读取保存的下载信息（文件指针位置）
	private void read_nPos() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(
					tmpFile));
			int nCount = input.readInt();
			nStartPos = new long[nCount];
			nEndPos = new long[nCount];
			for (int i = 0; i < nStartPos.length; i++) {
				nStartPos[i] = input.readLong();
				nEndPos[i] = input.readLong();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processErrorCode(int nErrorCode) {
		System.out.println("Error Code : " + nErrorCode);
	}

	// 停止文件下载
	public void siteStop() {
		bStop = true;
		for (int i = 0; i < nStartPos.length; i++)
			fileSplitterFetch[i].splitterStop();
	}

	public void addDownloadEventListener(DownloadEventListener listener) {
		listeners.add(listener);
	}
}
