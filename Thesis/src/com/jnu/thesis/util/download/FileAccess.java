package com.jnu.thesis.util.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class FileAccess implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1251337565634280578L;
	RandomAccessFile oSavedFile;
	long nPos;

	public FileAccess() throws IOException {
		this("", 0);
	}

	public FileAccess(String sName, long nPos) throws IOException {
		oSavedFile = new RandomAccessFile(sName, "rw");
		this.nPos = nPos;
		oSavedFile.seek(nPos);
	}

	public synchronized int write(byte[] b, int nStart, int nLen) {
		int n = -1;
		try {
			oSavedFile.write(b, nStart, nLen);
			n = nLen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
}
