package com.ics.service.impl;

import com.ics.dao.RegistDAO;

import com.ics.service.RegistService;

public class RegistServiceImpl implements RegistService {
	private RegistDAO registDao;

	public RegistServiceImpl() {

	}

	@Override
	public int register(String pubkey, String sequence, String manufacture,
			String Ip, String mac, String os, String client_area, String Name,
			String device_info, String device_type, String EK) {
		// TODO Auto-generated method stub
		return registDao.register(pubkey, sequence, manufacture, Ip, mac, os, client_area, Name, device_info, device_type, EK);
	}
	
	public String getListInfo(){
		return registDao.getListInfo();
	}

}
