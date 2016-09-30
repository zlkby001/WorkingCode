package com.ics.service.impl;

import com.ics.dao.AuditLogDao;
import com.ics.service.AuditLogService;

public class AuditLogServiceImpl implements AuditLogService{
	private AuditLogDao auditlogdao;
	public boolean uploadAuditLog(String auditlog)
	{
		return auditlogdao.uploadAuditLog(auditlog);
	}
	public String downloadadminpasswd(){
		return auditlogdao.downloadadminpasswd();
	}
}
