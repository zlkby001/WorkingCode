package com.ics.service;

public interface RegistService {

	public int register(String pubkey, String sequence,
			String manufacture, String Ip, String mac,
			String os, String client_area, String Name,
			String device_info, String device_type, String EK);

}
