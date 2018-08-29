package com.kosta.posts.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.kosta.posts.model.Post;

@Repository
public class PostsDaoImplInMemory implements PostsDao {

	private Map<Integer, Post> postMap;
	private int index;
	
	public PostsDaoImplInMemory() {
		this.postMap = new HashMap<>();
	}
	
	@Override
	public Post insertNewPost(Post post) {
		post.setPostId(++index);
		this.postMap.put(index, post);
		return post;
	}

	@Override
	public List<Post> selectAllPost() {
		return postMap.entrySet()
				.stream()
				.map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	@Override
	public Post selectOnePost(int postId) {
		return postMap.get(postId);
	}

	@Override
	public Post updateOnePost(Post post) {
		
		Post originalPost = this.selectOnePost((int)post.getPostId());
		if(post.getSubject() != null && post.getSubject().length() > 0){
			originalPost.setSubject(post.getSubject());
		}
		if(post.getContent() != null && post.getContent().length() > 0){
			originalPost.setContent(post.getContent());
		}
		
		postMap.put((int)post.getPostId(), originalPost);
		
		return originalPost;
		
	}

	@Override
	public void deleteOnePost(int postId) {
		postMap.remove(postId);
	}

}
