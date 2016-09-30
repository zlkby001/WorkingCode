package com.ics.service.impl;

import com.ics.dao.MetricDAO;
import com.ics.service.MetricService;

public class MetricServiceImpl implements MetricService {
	private MetricDAO metricDao;

	public MetricServiceImpl() {
	}

	@Override
	public String queryMetricTime(String TCM£ßPK, String metric_time) {
		// TODO Auto-generated method stub
		return metricDao.queryMetricTime(TCM£ßPK, metric_time);
	}

	@Override
	public boolean uploadMetricLog(String TCM_PK, String metriccontent) {
		// TODO Auto-generated method stub
		return metricDao.uploadMetricLog(TCM_PK, metriccontent);
	}

}
