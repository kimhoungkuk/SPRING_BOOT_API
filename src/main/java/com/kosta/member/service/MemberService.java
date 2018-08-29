package com.kosta.member.service;

import java.util.List;

import com.kosta.member.model.Member;

public interface MemberService {

	public Member addNewMember(Member member);
	
	public List<Member> findAllMember();

	public void deleteOnePost(int memberIndex);
	
}
