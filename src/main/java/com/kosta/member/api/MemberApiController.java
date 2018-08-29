package com.kosta.member.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.exceptions.ApiError;
import com.kosta.exceptions.ApiException;
import com.kosta.member.model.Member;
import com.kosta.member.service.MemberService;
import com.kosta.posts.model.Post;
import com.kosta.response.ApiDataResponse;
import com.kosta.response.ApiResponse;

@RestController
public class MemberApiController {
	
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/member")
	public ApiDataResponse<Member> registMember(@Valid @RequestBody Member member, Errors errors){

		if( errors.hasErrors()){
			String message = errors.getFieldErrors()
					.stream()
					.limit(1)
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.joining(""));

			throw new ApiException(ApiError.MISSING_PARAMETER,message);
			/*throw new RuntimeException(message);*/
		}
		
		Member registeredMember = memberService.addNewMember(member);
		return new ApiDataResponse<Member>(registeredMember);
		

	}
	
	@GetMapping("/member")
	public ApiDataResponse<List<Member>> getAllMember(){
		
		List<Member> allMemberList = memberService.findAllMember();
		return new ApiDataResponse<List<Member>>(allMemberList);

	}
	
	
	@DeleteMapping("/member/{memberIndex}")
	public ApiResponse deleteOneMember(@PathVariable int memberIndex){
		
		memberService.deleteOnePost(memberIndex);
		return new ApiResponse();
		
	}
}
