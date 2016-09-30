package com.ics.service.impl;
import com.ics.dao.DownloadUsbDAO;
import com.ics.service.DownloadUsbService;

public class DownloadUsbServiceImp implements DownloadUsbService{
	private DownloadUsbDAO downloadusbdao;
	public String downloadusb(String pk)
	{
		return downloadusbdao.downloadusb(pk);
	}
	public boolean uploadusb(String usbinfo)
	{
		return downloadusbdao.uploadusb(usbinfo);
	}

}
