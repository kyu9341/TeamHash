package com.bs.lec18.member.service;

import com.bs.lec18.member.Member;

public interface IMemberService {
	void memberRegister(String memId, String memPw, String memMail, String memPhone1, String memPhone2, String memPhone3);
	void memberRegister(Member member);
	Member memberSearch(String memId, String memPw);
	void memberModify();
	void memberRemove();
}
