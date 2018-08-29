package com.kosta.posts.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.exceptions.ApiError;
import com.kosta.exceptions.ApiException;
import com.kosta.posts.model.Post;
import com.kosta.posts.service.PostsService;
import com.kosta.response.ApiDataResponse;
import com.kosta.response.ApiResponse;

@RestController
public class PostApiController {
	
	@Autowired
	private PostsService postsService;
	
	@PostMapping("/posts")
	//public ApiDataResponse<Post> registPost(@RequestBody Post post){
	public ApiResponse registPost(@Valid @RequestBody Post post, Errors errors){
		
		// NumberFormatException : for input "ABC"
		//Integer.parseInt("AA");
/*		
		System.out.println(post.getSubject());
		System.out.println(post.getContent());*/
		
/*		if( post.getSubject() == null || post.getSubject().length() == 0){
			return new ApiResponse("subject is required");
		}
	
		if( post.getContent() == null || post.getContent().length() == 0){
			return new ApiResponse("content is required");
		}*/
		
		if( errors.hasErrors()){
/*			for( FieldError error : errors.getFieldErrors()){
				String messageString = error.getDefaultMessage(); // @NotEmpty(message="subject is required") messge 가 DefaultMessage
				return new ApiResponse(messageString);
			}*/
			
			String message = errors.getFieldErrors()
					.stream()
					.limit(1)
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.joining(""));
			
/*			Java8 
			함수형 API 
			1. Stream
				- FieldError / FieldError
				- Stream<FiledError>
			    - .limit(1) : 첫번째 FieldError
			    - .map : 첫번째 FieldError 의  getDefaultMessage 가져와서 리턴 : Stream<String>
			    - .joining : 
			2. Lambda
			*/
						
			throw new ApiException(ApiError.MISSING_PARAMETER,message);
			/*return new ApiResponse(message);*/
		}
		
		Post registeredPost = postsService.addNewPost(post);
		
		return new ApiDataResponse<Post>(registeredPost);
		
/*		Map<String, Object> result = new HashMap<>(); 
		
		result.put("status", "OK");
		result.put("data", registeredPost);
		result.put("error", "");
		
		return result;*/
	}
	
	@GetMapping("/posts")
	public ApiDataResponse<List<Post>> getAllPosts(
			@RequestParam(required=false) String subject,
			@RequestParam(required=false, defaultValue="1000") int limit
	){
		
		List<Post> allPostsList = postsService.findAllPost();
		return new ApiDataResponse<List<Post>>(allPostsList);
				 
/*		Map<String, Object> result = new HashMap<>();
		result.put("status", "OK");
		result.put("data", allPostsList);
		result.put("error", "");
		
		return result;*/
		
	}
	
	// /posts/{postId}
	@GetMapping("/posts/{postId}") // Path Parameter : postId
	public ApiDataResponse<Post> getOnePost(@PathVariable int postId){
		
		return new ApiDataResponse<Post>(postsService.findOnePost(postId));
		
/*		Map<String, Object> result = new HashMap<>();
		result.put("status", "OK");
		result.put("data", postsService.findOnePost(postId));
		result.put("error", "");
		
		return result;*/
		
	}
	
	// /posts/{postId}
	@PutMapping("/posts/{postId}") // Path Parameter : postId
	public ApiDataResponse<Post> updateOnePost(
			@PathVariable int postId 
			, @RequestBody Post post ){
		
		return new ApiDataResponse<Post>(postsService.updateOnePost(postId, post));
		
/*		Map<String, Object> result = new HashMap<>();
		result.put("status", "OK");
		result.put("data", postsService.updateOnePost(postId, post));
		result.put("error", "");
		
		return result;*/
		
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deleteOnePost(@PathVariable int postId){
		
		postsService.deleteOnePost(postId);
		return new ApiResponse();
		
	}
	
}
