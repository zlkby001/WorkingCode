package com.ics.service.impl;

import com.ics.dao.KnowledgeDAO;
import com.ics.service.KnowledgeService;

public class KnowledgeServiceImpl implements KnowledgeService{
	private KnowledgeDAO knowledgeDao;
	@Override
	public String queryknowledge(String TCM_PK, String hash_values) {
		// TODO Auto-generated method stub
		return knowledgeDao.queryknowledge(TCM_PK, hash_values);
	}

}
