package com.jnu.thesis.util.download;

public class DownloadInfoBean {
	private String sSiteURL; // 下载文件 URL
	private String sFilePath; // 文件保存路径
	private String sFileName; // 保存文件名字
	private int nSplitter; // 下载线程数

	public DownloadInfoBean(String sSiteURL, String sFilePath,
			String sFileName, int nSplitter) {
		super();
		this.sSiteURL = sSiteURL;
		this.sFilePath = sFilePath;
		this.sFileName = sFileName;
		this.nSplitter = nSplitter;
	}

	public DownloadInfoBean(String sSiteURL, String sFilePath, int nSplitter) {
		super();
		this.sSiteURL = sSiteURL;
		this.sFilePath = sFilePath;
		this.nSplitter = nSplitter;
		this.sFileName = sSiteURL.substring(sSiteURL.lastIndexOf("=") + 1,
				sSiteURL.length());
	}

	public String getsSiteURL() {
		return sSiteURL;
	}

	public void setsSiteURL(String sSiteURL) {
		this.sSiteURL = sSiteURL;
	}

	public String getsFilePath() {
		return sFilePath;
	}

	public void setsFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}

	public String getsFileName() {
		return sFileName;
	}

	public void setsFileName(String sFileName) {
		this.sFileName = sFileName;
	}

	public int getnSplitter() {
		return nSplitter;
	}

	public void setnSplitter(int nSplitter) {
		this.nSplitter = nSplitter;
	}
}
