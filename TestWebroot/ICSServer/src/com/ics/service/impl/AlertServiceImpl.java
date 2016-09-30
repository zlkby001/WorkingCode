package com.ics.service.impl;

import com.ics.dao.AlertDao;
import com.ics.service.AlertService;

public  class AlertServiceImpl implements AlertService{
	private AlertDao alertdao;
	public boolean uploadalert(String alertinfo)
	{
		return alertdao.uploadalert(alertinfo);
	}
}
