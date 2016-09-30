package com.ics.model;

/**
 * @author chen
 * 
 */
public class Knowledge {
	private String hash_value;
	private String process_name;
	private String software_ver;
	private String os_ver;
	private String software_info;
	private String release_date;
	private String manufacturer;
	private String ic_bool;
	private String is_recommended;
	private int hash_count;
	private String ics_name;
	private String current_statsid;

	public Knowledge() {
	}

	public Knowledge(String hash_value, String process_name,
			String software_ver, String os_ver, String software_info,
			String release_date, String manufacturer, String ic_bool,
			String is_recommended, int hash_count, String ics_name,
			String current_statsid) {
		super();
		this.hash_value = hash_value;
		this.process_name = process_name;
		this.software_ver = software_ver;
		this.os_ver = os_ver;
		this.software_info = software_info;
		this.release_date = release_date;
		this.manufacturer = manufacturer;
		this.ic_bool = ic_bool;
		this.is_recommended = is_recommended;
		this.hash_count = hash_count;
		this.ics_name = ics_name;
		this.current_statsid = current_statsid;
	}

	public String getHash_value() {
		return hash_value;
	}

	public void setHash_value(String hash_value) {
		this.hash_value = hash_value;
	}

	public String getProcess_name() {
		return process_name;
	}

	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}

	public String getSoftware_ver() {
		return software_ver;
	}

	public void setSoftware_ver(String software_ver) {
		this.software_ver = software_ver;
	}

	public String getOs_ver() {
		return os_ver;
	}

	public void setOs_ver(String os_ver) {
		this.os_ver = os_ver;
	}

	public String getSoftware_info() {
		return software_info;
	}

	public void setSoftware_info(String software_info) {
		this.software_info = software_info;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getIc_bool() {
		return ic_bool;
	}

	public void setIc_bool(String ic_bool) {
		this.ic_bool = ic_bool;
	}

	public String getIs_recommended() {
		return is_recommended;
	}

	public void setIs_recommended(String is_recommended) {
		this.is_recommended = is_recommended;
	}

	public int getHash_count() {
		return hash_count;
	}

	public void setHash_count(int hash_count) {
		this.hash_count = hash_count;
	}

	public String getCurrent_statsid() {
		return current_statsid;
	}

	public void setCurrent_statsid(String current_statsid) {
		this.current_statsid = current_statsid;
	}

	public String getIcs_name() {
		return ics_name;
	}

	public void setIcs_name(String ics_name) {
		this.ics_name = ics_name;
	}

	@Override
	public String toString() {
		return "Knowledge [hash_value=" + hash_value + ", process_name="
				+ process_name + ", software_ver=" + software_ver + ", os_ver="
				+ os_ver + ", software_info=" + software_info
				+ ", release_date=" + release_date + ", manufacturer="
				+ manufacturer + ", ic_bool=" + ic_bool + ", is_recommended="
				+ is_recommended + ", hash_count=" + hash_count + ", ics_name="
				+ ics_name + ", current_statsid=" + current_statsid + "]";
	}

}
