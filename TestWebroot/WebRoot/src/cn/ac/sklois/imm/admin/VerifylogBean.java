package cn.ac.sklois.imm.admin;

public class VerifylogBean {
	private String ip;
	private String pubkey;
	private String name;
	private String date;
	private int userid;
	
	public String getip()
	{
		return this.ip;
	}
	
	public String getpubkey()
	{
		return this.pubkey;
	}
	
	public String getname()
	{
		return this.name;
	}
	
	public String getdate()
	{
		return this.date;
	}
	
	public int getuserid()
	{
		return this.userid;
	}
	
	public void setpubkey(String pubkey)
	{
		this.pubkey=pubkey;
	}
	
	public void setname(String name)
	{
		this.name=name;
	}
	
	public void setdate(String date)
	{
		this.date=date;
	}
	
	public void setip(String ip)
	{
		this.ip=ip;
	}
	
	public void setuserid(int userid)
	{
		this.userid=userid;
	}
	
	public VerifylogBean()
	{
		ip=null;
		date=null;
		pubkey=null;
		name=null;
		userid=0;
	}

}
