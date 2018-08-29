package com.kosta.member.dao;

import java.util.List;

import com.kosta.member.model.Member;

public interface MemberDao {
	
	public Member insertNewMember(Member member);
	
	public List<Member> selectAllMember();
	
	public void deleteOnePost(int memberIndex);
	
}
