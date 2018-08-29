package com.kosta.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kosta.member.model.Member;
import com.kosta.posts.model.Post;

@Repository
public class MemberDaoImplInMemory implements MemberDao {

	private Map<Integer, Member> memberMap;
	private int memberIndex;

	public MemberDaoImplInMemory() {
		this.memberMap = new HashMap<>();
	}

	@Override
	public Member insertNewMember(Member member) {
		member.setMemberIndex(++memberIndex);
		this.memberMap.put(memberIndex, member);
		return member;
	}

	@Override
	public List<Member> selectAllMember() {
		return memberMap.entrySet().stream().map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	@Override
	public void deleteOnePost(int memberIndex) {
		memberMap.remove(memberIndex);
	}


}
