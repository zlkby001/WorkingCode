package com.ics.service;

public interface MetricService {

	public String queryMetricTime(String TCM��PK, String metric_time);

	public boolean uploadMetricLog(String TCM_PK, String metriccontent);

}
