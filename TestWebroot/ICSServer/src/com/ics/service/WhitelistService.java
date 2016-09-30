package com.ics.service;

public interface WhitelistService {

	public boolean uploadWhitelist(String TCM_PK, int whitelist_vern,
			String whitelistcontent);

	public String downloadWhitelist(String TCM_PK, int whitelist_vern);

	public String queryWhitelistinfo(String TCM_PK);
}
