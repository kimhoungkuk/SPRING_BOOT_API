package com.kosta.posts.service;

import java.util.List;

import com.kosta.posts.model.Post;

public interface PostsService {
	
	public Post addNewPost(Post post);
	
	public List<Post> findAllPost();

	public Post findOnePost(int postId);
	
	public Post updateOnePost(int postId, Post post);
	
	public void deleteOnePost(int postId);
	
}
