package com.bs.lec19.member;

import java.util.List;

public class Member {
	
	private String memId;
	private String memPw;
	private String memMail;
	private List<MemPhone> memPhones;
	private int memAge;
	private boolean memAdult;
	private String memGender;
	private String[] memFSports;
	
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
	public List<MemPhone> getMemPhones() {
		return memPhones;
	}
	public void setMemPhones(List<MemPhone> memPhones) {
		this.memPhones = memPhones;
	}
	public int getMemAge() {
		return memAge;
	}
	public void setMemAge(int memAge) {
		this.memAge = memAge;
	}
	public boolean isMemAdult() {
		return memAdult;
	}
	public void setMemAdult(boolean memAdult) {
		this.memAdult = memAdult;
	}
	public String getMemGender() {
		return memGender;
	}
	public void setMemGender(String memGender) {
		this.memGender = memGender;
	}
	public String[] getMemFSports() {
		return memFSports;
	}
	public void setMemFSports(String[] memFSports) {
		this.memFSports = memFSports;
	}
	
}