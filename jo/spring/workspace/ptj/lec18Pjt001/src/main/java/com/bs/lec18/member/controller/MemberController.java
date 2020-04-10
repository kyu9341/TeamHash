package com.bs.lec18.member.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bs.lec18.member.Member;
import com.bs.lec18.member.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

//	MemberService service = new MemberService();
//	@Autowired
	@Resource(name="memService")
	MemberService service;
	
	/*
	@RequestMapping(value="/memJoin", method=RequestMethod.POST)
	public String memJoin(Model model, 
			@RequestParam("memId") String memId, 
			@RequestParam("memPw") String memPw, 
			@RequestParam("memMail") String memMail, 
			@RequestParam("memPhone1") String memPhone1, 
			@RequestParam("memPhone2") String memPhone2, 
			@RequestParam("memPhone3") String memPhone3, 
			@RequestParam("memAge") int memAge, 
			@RequestParam(value="memGender", defaultValue="M") String memGender) {
		
		service.memberRegister(memId, memPw, memMail, memPhone1, memPhone2, memPhone3);
		
		model.addAttribute("memId", memId);
		model.addAttribute("memPw", memPw);
		model.addAttribute("memMail", memMail);
		model.addAttribute("memPhone", memPhone1 + " - " + memPhone2 + " - " + memPhone3);
		
		return "memJoinOk";
	}
	*/
	
	@RequestMapping(value="/memJoin", method=RequestMethod.POST)
	public String memJoin(Member member) {
		
		service.memberRegister(member);
		
		return "memJoinOk";
	}
	
	@RequestMapping(value="/memLogin", method=RequestMethod.POST)
	public String memLogin(Model model, 
			@RequestParam("memId") String memId, 
			@RequestParam("memPw") String memPw) {
		
		Member member = service.memberSearch(memId, memPw);
		
		try {
			model.addAttribute("memId", member.getMemId());
			model.addAttribute("memPw", member.getMemPw());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "memLoginOk";
	}
	
}