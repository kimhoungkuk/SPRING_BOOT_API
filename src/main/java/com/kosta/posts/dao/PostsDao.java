package com.kosta.posts.dao;

import java.util.List;

import com.kosta.posts.model.Post;

public interface PostsDao {
	
	public Post insertNewPost(Post post);
	
	public List<Post> selectAllPost();
	
	public Post selectOnePost(int postId);
	
	public Post updateOnePost(Post post);
	
	public void deleteOnePost(int postId);
	
}
