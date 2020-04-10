package com.bs.lec23.member.dao;

import java.util.Map;

import com.bs.lec23.member.Member;

public interface IMemberDao {
	int memberInsert(Member member);
	Member memberSelect(Member member);
	int memberUpdate(Member member);
	int memberDelete(Member member);
}