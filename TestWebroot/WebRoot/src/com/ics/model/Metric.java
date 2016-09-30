package com.ics.model;

public class Metric {
	private int metric_id;// 序号
	private String TCM_PK;// TCK公钥
	private String client_id;// 客户端序号
	private String process_name;// 进程名
	private String process_path;// 进程位置
	private String hash_value;// Hash值
	private String pprocess_name;// 父进程名
	private String pprocess_id;// 父进程ID
	private String metric_time;// 度量时间
	private String software_ver;// 软件版本号
	private String os_ver;// 操作系统号
	private String software_info;// 软件信息
	private String release_date;// 发布日期
	private String manufacturer;// 厂商信息
	private String ic_bool;// 工控软件
	private String ics_name;// 知识库名字

	public Metric() {
	}

	
	public Metric(int metric_id, String tCM_PK, String client_id,
			String process_name, String process_path, String hash_value,
			String pprocess_name, String pprocess_id, String metric_time,
			String software_ver, String os_ver, String software_info,
			String release_date, String manufacturer, String ic_bool,
			String ics_name) {
		super();
		this.metric_id = metric_id;
		TCM_PK = tCM_PK;
		this.client_id = client_id;
		this.process_name = process_name;
		this.process_path = process_path;
		this.hash_value = hash_value;
		this.pprocess_name = pprocess_name;
		this.pprocess_id = pprocess_id;
		this.metric_time = metric_time;
		this.software_ver = software_ver;
		this.os_ver = os_ver;
		this.software_info = software_info;
		this.release_date = release_date;
		this.manufacturer = manufacturer;
		this.ic_bool = ic_bool;
		this.ics_name = ics_name;
	}


	public int getMetric_id() {
		return metric_id;
	}

	public void setMetric_id(int metric_id) {
		this.metric_id = metric_id;
	}

	public String getTCM_PK() {
		return TCM_PK;
	}

	public void setTCM_PK(String tCM_PK) {
		TCM_PK = tCM_PK;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getProcess_name() {
		return process_name;
	}

	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}

	public String getProcess_path() {
		return process_path;
	}

	public void setProcess_path(String process_path) {
		this.process_path = process_path;
	}

	public String getHash_value() {
		return hash_value;
	}

	public void setHash_value(String hash_value) {
		this.hash_value = hash_value;
	}

	public String getPprocess_name() {
		return pprocess_name;
	}

	public void setPprocess_name(String pprocess_name) {
		this.pprocess_name = pprocess_name;
	}

	public String getPprocess_id() {
		return pprocess_id;
	}

	public void setPprocess_id(String pprocess_id) {
		this.pprocess_id = pprocess_id;
	}

	public String getMetric_time() {
		return metric_time;
	}

	public void setMetric_time(String metric_time) {
		this.metric_time = metric_time;
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

	public String getIcs_name() {
		return ics_name;
	}

	public void setIcs_name(String ics_name) {
		this.ics_name = ics_name;
	}


	@Override
	public String toString() {
		return "Metric [metric_id=" + metric_id + ", TCM_PK=" + TCM_PK
				+ ", client_id=" + client_id + ", process_name=" + process_name
				+ ", process_path=" + process_path + ", hash_value="
				+ hash_value + ", pprocess_name=" + pprocess_name
				+ ", pprocess_id=" + pprocess_id + ", metric_time="
				+ metric_time + ", software_ver=" + software_ver + ", os_ver="
				+ os_ver + ", software_info=" + software_info
				+ ", release_date=" + release_date + ", manufacturer="
				+ manufacturer + ", ic_bool=" + ic_bool + ", ics_name="
				+ ics_name + "]";
	}

}
