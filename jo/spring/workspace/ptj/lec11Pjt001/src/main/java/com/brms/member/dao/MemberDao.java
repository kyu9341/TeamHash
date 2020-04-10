package com.brms.member.dao;

import java.util.HashMap;
import java.util.Map;

import com.brms.member.Member;

public class MemberDao {

	
	private Map<String, Member> memberDB = 
			new HashMap<String, Member>();
	
	public void insert(Member member) {
		memberDB.put(member.getmId(), member);
	}
	
	public Member select(String mId){
		return memberDB.get(mId);
		
	}
	
	public void update(Member member) {
		
	}
	
	public void delete(String mId){
		
	}
	
	public Map<String, Member> getMemberDB() {
		return memberDB;
	}
	
}
