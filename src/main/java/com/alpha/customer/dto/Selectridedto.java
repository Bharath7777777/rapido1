package com.alpha.customer.dto;

import com.alpha.customer.entity.Coordinates;

public class Selectridedto {
	private int cid;
	private String pickuploc;
	private String droploc;
	private Coordinates sourcecoordinate;
	private Coordinates destcoordinate;
	public Selectridedto(int cid, String pickuploc, String droploc, Coordinates sourcecoordinate,
			Coordinates destcoordinate) {
		super();
		this.cid = cid;
		this.pickuploc = pickuploc;
		this.droploc = droploc;
		this.sourcecoordinate = sourcecoordinate;
		this.destcoordinate = destcoordinate;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getPickuploc() {
		return pickuploc;
	}
	public void setPickuploc(String pickuploc) {
		this.pickuploc = pickuploc;
	}
	public String getDroploc() {
		return droploc;
	}
	public void setDroploc(String droploc) {
		this.droploc = droploc;
	}
	public Coordinates getSourcecoordinate() {
		return sourcecoordinate;
	}
	public void setSourcecoordinate(Coordinates sourcecoordinate) {
		this.sourcecoordinate = sourcecoordinate;
	}
	public Coordinates getDestcoordinate() {
		return destcoordinate;
	}
	public void setDestcoordinate(Coordinates destcoordinate) {
		this.destcoordinate = destcoordinate;
	}
	public Selectridedto() {
		super();
	}
	
	
	
}
