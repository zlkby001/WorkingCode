package com.ics.dao;

public interface MetricDAO {
	
	public String queryMetricTime(String TCM��PK, String metric_time);

	public boolean uploadMetricLog(String TCM_PK, String metriccontent);
	
}
