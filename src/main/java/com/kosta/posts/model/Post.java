package com.kosta.posts.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Post {

	private long postId;
	@NotEmpty(message="subject is required")
	private String subject;
	@NotEmpty(message="content is required")
	private String content;

	public Post(){
		
	}
	
	// alt+shift+s
	public Post(long postId, String subject, String content) {
		this.postId = postId;
		this.subject = subject;
		this.content = content;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
