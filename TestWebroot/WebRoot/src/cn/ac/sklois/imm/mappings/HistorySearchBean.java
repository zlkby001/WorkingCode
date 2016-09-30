package cn.ac.sklois.imm.mappings;

public class HistorySearchBean {
	private int sclass;
	private String softwareName;
	private String sname;
	private String sdate;
	private int avalue;
	
	public int getsclass(){
		return this.sclass;
	}
	
	public String getsoftwareName(){
		return this.softwareName;
	}
	
	public String getsname(){
		return this.sname;
	}
	
	public String getsdate(){
		return this.sdate;
	}
	
	public int getavalue(){
		return this.avalue;
	}
	
	public void setsclass(int sclass){
		if(sclass<0||sclass>12)
			return;
		this.sclass=sclass;
	}
	
	public void setsoftwareName(String softwareName){
		if(softwareName=="")
			return;
		this.softwareName=softwareName;
	}
	
	public void setsname(String sname){
		if(sname=="")
			return;
		this.sname=sname;
	}
	
	public void setsdate(String sdate){
		if(sdate=="")
			return;
		this.sdate=sdate;
	}
	
	public void setavalue(int avalue){
		if(avalue<0||avalue>2)
			return;
		this.avalue=avalue;
	}
	
	public HistorySearchBean(){
		this.avalue=2;
		this.sclass=0;
		this.sdate=null;
		this.softwareName=null;
		this.sname=null;
	}
}
