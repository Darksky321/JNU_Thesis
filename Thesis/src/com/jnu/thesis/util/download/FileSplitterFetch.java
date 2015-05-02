package com.jnu.thesis.util.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileSplitterFetch extends Thread {
	String sURL; // File URL
	long nStartPos; // File Snippet Start Position
	long nEndPos; // File Snippet End Position
	long bytesRead; // Bytes finished
	int nThreadID; // Thread's ID
	boolean bDownOver = false; // Downing is over
	boolean bStop = false; // Stop identical
	FileAccess fileAccess = null; // File Access interface

	public FileSplitterFetch(String sURL, String sName, long nStart, long nEnd,
			int id) throws IOException {
		this.sURL = sURL;
		this.nStartPos = nStart;
		this.nEndPos = nEnd;
		nThreadID = id;
		fileAccess = new FileAccess(sName, nStartPos);
	}

	public void run() {
		bytesRead = 0;
		while (nStartPos < nEndPos && !bStop) {
			try {
				URL url = new URL(sURL);
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + nStartPos + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				Utility.log(sProperty);
				InputStream input = httpConnection.getInputStream();
				// logResponseHead(httpConnection);
				byte[] b = new byte[1024];
				int nRead;
				while ((nRead = input.read(b, 0, 1024)) > 0
						&& nStartPos < nEndPos && !bStop) {
					nStartPos += fileAccess.write(b, 0, nRead);
					bytesRead += nRead;
					// System.out.println("Thread" + nThreadID + ":" +
					// bytesRead);
					// if(nThreadID == 1)
					// Utility.log("nStartPos = " + nStartPos + ", nEndPos = " +
					// nEndPos);
				}
				Utility.log("Thread " + nThreadID + " is over!" + bytesRead);
				bDownOver = true;
				// nPos = fileAccessI.write (b,0,nRead);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ��ӡ��Ӧ��ͷ��Ϣ
	public void logResponseHead(HttpURLConnection con) {
		for (int i = 1;; i++) {
			String header = con.getHeaderFieldKey(i);
			if (header != null)
				// responseHeaders.put(header,httpConnection.getHeaderField(header));
				Utility.log(header + " : " + con.getHeaderField(header));
			else
				break;
		}
	}

	public void splitterStop() {
		bStop = true;
	}
}
