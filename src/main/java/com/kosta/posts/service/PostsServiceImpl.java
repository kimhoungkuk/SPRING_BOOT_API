package com.kosta.posts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kosta.posts.dao.PostsDao;
import com.kosta.posts.model.Post;

@Service
public class PostsServiceImpl implements PostsService {

	@Autowired
	@Qualifier("com.kosta.posts.dao.PostsDaoImplH2")
	private PostsDao postsDao;  
	
	@Override
	public Post addNewPost(Post post) {
		return postsDao.insertNewPost(post);
	}

	@Override
	public List<Post> findAllPost() {
		return postsDao.selectAllPost();
	}

	@Override
	public Post findOnePost(int postId) {
		return postsDao.selectOnePost(postId);
	}

	@Override
	public Post updateOnePost(int postId, Post post) {
		post.setPostId(postId);
		return postsDao.updateOnePost(post);
	}

	@Override
	public void deleteOnePost(int postId) {
		postsDao.deleteOnePost(postId);
	}

}
