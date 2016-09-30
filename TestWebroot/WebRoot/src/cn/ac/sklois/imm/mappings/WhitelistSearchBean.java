package cn.ac.sklois.imm.mappings;

public class WhitelistSearchBean {
	private String sname;
	private String sdigest;
	
	public String getsname()
	{
		return this.sname;
	}
	
	public String getsdigest()
	{
		return this.sdigest;
	}
	
	public void setsname(String sname)
	{
		this.sname=sname;
	}
	
	public void setsdigest(String sdigest)
	{
		this.sdigest=sdigest;
	}
	
	public WhitelistSearchBean()
	{
		this.sname=null;
		this.sdigest=null;
	}

}
