package com.keduit2;

public class petInfoField {
	private String desertionNo;
	private String orgNm;
	private String kindCd;
	private String sexCd;
	private String colorCd;
	private String age;
	private String happenPlace;
	private String careNm;
	private String officetel;
	private String popfile;
	private String processState;

	public petInfoField(String desertionNo, String orgNm, String kindCd, String sexCd, String colorCd, String age,
			String happenPlace, String careNm, String officetel, String popfile, String processState) {

		this.desertionNo = desertionNo;
		this.orgNm = orgNm;
		this.kindCd = kindCd;
		this.sexCd = sexCd;
		this.colorCd = colorCd;
		this.age = age;
		this.happenPlace = happenPlace;
		this.careNm = careNm;
		this.officetel = officetel;
		this.popfile = popfile;
		this.processState = processState;
	}

	public String getDesertionNo() {
		return desertionNo;
	}

	public void setDesertionNo(String desertionNo) {
		this.desertionNo = desertionNo;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getKindCd() {
		return kindCd;
	}

	public void setKindCd(String kindCd) {
		this.kindCd = kindCd;
	}

	public String getSexCd() {
		return sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHappenPlace() {
		return happenPlace;
	}

	public void setHappenPlace(String happenPlace) {
		this.happenPlace = happenPlace;
	}

	public String getCareNm() {
		return careNm;
	}

	public void setCareNm(String careNm) {
		this.careNm = careNm;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getpopfile() {
		return popfile;
	}

	public void setpopfile(String popfile) {
		this.popfile = popfile;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

}
