package com.kosta.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.member.dao.MemberDao;
import com.kosta.member.model.Member;
import com.kosta.posts.model.Post;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public Member addNewMember(Member member) {
		return memberDao.insertNewMember(member);
	}

	@Override
	public List<Member> findAllMember() {
		return memberDao.selectAllMember();
	}

	@Override
	public void deleteOnePost(int memberIndex) {
		memberDao.deleteOnePost(memberIndex);
	}
}
