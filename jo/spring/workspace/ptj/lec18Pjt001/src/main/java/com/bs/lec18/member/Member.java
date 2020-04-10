package com.bs.lec18.member;

public class Member {
	private String memId;
	private String memPw;
	private String memMail;
//	private String memPhone1;
//	private String memPhone2;
//	private String memPhone3;
	private MemPhone memPhone;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
	public String getMemMail() {
		return memMail;
	}
	public void setMemMail(String memMail) {
		this.memMail = memMail;
	}
	/*
	public String getMemPhone1() {
		return memPhone1;
	}
	public void setMemPhone1(String memPhone1) {
		this.memPhone1 = memPhone1;
	}
	public String getMemPhone2() {
		return memPhone2;
	}
	public void setMemPhone2(String memPhone2) {
		this.memPhone2 = memPhone2;
	}
	public String getMemPhone3() {
		return memPhone3;
	}
	public void setMemPhone3(String memPhone3) {
		this.memPhone3 = memPhone3;
	}
	*/
	public MemPhone getMemPhone() {
		return memPhone;
	}
	public void setMemPhone(MemPhone memPhone) {
		this.memPhone = memPhone;
	}
	
}
