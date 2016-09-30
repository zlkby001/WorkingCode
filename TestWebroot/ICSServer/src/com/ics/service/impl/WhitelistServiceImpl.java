package com.ics.service.impl;

import com.ics.dao.WhitelistDAO;
import com.ics.service.WhitelistService;

public class WhitelistServiceImpl implements WhitelistService {
	private WhitelistDAO whitelistDao;

	public WhitelistServiceImpl() {
	}

	@Override
	public boolean uploadWhitelist(String TCM_PK, int whitelist_vern,
			String whitelistcontent) {
		// TODO Auto-generated method stub
		return whitelistDao.uploadWhitelist(TCM_PK, whitelist_vern,
				whitelistcontent);
	}

	@Override
	public String downloadWhitelist(String TCM_PK, int whitelist_vern) {
		// TODO Auto-generated method stub
		return whitelistDao.downloadWhitelist(TCM_PK, whitelist_vern);
	}

	@Override
	public String queryWhitelistinfo(String TCM_PK) {
		// TODO Auto-generated method stub
		return whitelistDao.queryWhitelistinfo(TCM_PK);
	}

}
