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
	private DownloadInfoBean siteInfoBean = null; // �ļ���Ϣ Bean
	private long[] nStartPos; // ��ʼλ��
	private long[] nEndPos; // ����λ��
	private FileSplitterFetch[] fileSplitterFetch; // ���̶߳���
	private long nFileLength; // �ļ�����
	private long nBytesSaved; // �ѱ����ֽ���
	private long nBytesRead; // �Ѷ��ֽ���
	private long nBytes; // �ѱ�����Ѷ�
	private boolean bFirst = true; // �Ƿ��һ��ȡ�ļ�
	private boolean bStop = false; // ֹͣ��־
	private File tmpFile; // �ļ����ص���ʱ��Ϣ
	private DataOutputStream output; // ������ļ��������
	private List<DownloadEventListener> listeners = new ArrayList<DownloadEventListener>();
	private Context context;

	public DownloadTask(DownloadInfoBean bean, Context context)
			throws IOException { // ��ʼ��������Ϣ,���Ҫ���жϵ�����,���ʼ��������ʼλ��
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
		// ����ļ�����
		// �ָ��ļ�
		// ʵ�� FileSplitterFetch
		// ���� FileSplitterFetch �߳�
		// �ȴ����̷߳���
		try {
			if (bFirst) { // ����ǵ�һ������,������ļ����ȳ�ʼ��������ʼ����λ��
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
			// �������߳�
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
			// �ȴ����߳̽���
			// int count = 0;
			// �Ƿ���� while ѭ��
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
				System.out.println("�ļ����ؽ�����");
				tmpFile.delete();
				Intent intent = new Intent();
				intent.setAction("com.jnu.thesis.DOWNLOAD_FINISH");
				context.sendBroadcast(intent);
			} else {
				System.out.println("�ļ�����δ��ɣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.toString());
		}
	} // ����ļ�����

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

	// ����������Ϣ���ļ�ָ��λ�ã�
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

	// ��ȡ�����������Ϣ���ļ�ָ��λ�ã�
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

	// ֹͣ�ļ�����
	public void siteStop() {
		bStop = true;
		for (int i = 0; i < nStartPos.length; i++)
			fileSplitterFetch[i].splitterStop();
	}

	public void addDownloadEventListener(DownloadEventListener listener) {
		listeners.add(listener);
	}
}
