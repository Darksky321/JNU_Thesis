package com.jnu.thesis.util.download;

import java.util.EventListener;

public interface DownloadEventListener extends EventListener {
	public void downloadFinished(DownloadEvent e);

	public void downloadStopped(DownloadEvent e);

	public void downloadFailed(DownloadEvent e);
}
