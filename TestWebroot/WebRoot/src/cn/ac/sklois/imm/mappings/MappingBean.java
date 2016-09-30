package cn.ac.sklois.imm.mappings;

public class MappingBean {
	private Attribute att;
	private MeasurementValues mvs;
	private Operation creation;
	private Operation modification;
	
	public Attribute getAtt() {
		return att;
	}
	public void setAtt(Attribute att) {
		this.att = att;
	}
	public MeasurementValues getMvs() {
		return mvs;
	}
	public void setMvs(MeasurementValues mvs) {
		this.mvs = mvs;
	}
	public Operation getCreation() {
		return creation;
	}
	public void setCreation(Operation creation) {
		this.creation = creation;
	}
	public Operation getModification() {
		return modification;
	}
	public void setModification(Operation modification) {
		this.modification = modification;
	}
	
	
	
}
