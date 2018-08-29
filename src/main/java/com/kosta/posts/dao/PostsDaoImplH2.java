package com.kosta.posts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kosta.posts.model.Post;

@Repository("com.kosta.posts.dao.PostsDaoImplH2")
public class PostsDaoImplH2 implements PostsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Post insertNewPost(Post post) {
		StringBuffer query = new StringBuffer();

		query.append(" INSERT INTO POST ( ");
		query.append("		SUBJECT       ");
		query.append("		, CONTENT     ");
		query.append(" )                  ");
		query.append(" VALUES (             ");
		query.append("		?             ");
		query.append("		,?            ");
		query.append(" )                  ");

		jdbcTemplate.update(query.toString(), post.getSubject(),
				post.getContent());

		return post;
	}

	@Override
	public List<Post> selectAllPost() {
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT POST_ID");
		query.append("	   ,SUBJECT ");
		query.append("	   ,CONTENT ");
		query.append("FROM POST     ");
		
		return jdbcTemplate.query(query.toString(), new RowMapper<Post>(){

			@Override
			public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Post(
						rs.getInt("POST_ID"), 
						rs.getString("SUBJECT"),
						rs.getString("CONTENT")
				);
			}
			
		});
	}

	@Override
	public Post selectOnePost(int postId) {
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT POST_ID");
		query.append("	   ,SUBJECT ");
		query.append("	   ,CONTENT ");
		query.append("FROM POST     ");
		query.append("WHERE POST_ID = ?    ");

		return jdbcTemplate.queryForObject(query.toString()
											, new Object[] {postId}
											, (rs, rum) -> new Post(
															rs.getInt("POST_ID")
															,rs.getString("SUBJECT")
															,rs.getString("CONTENT")));
	}

	@Override
	public Post updateOnePost(Post post) {
		StringBuffer query = new StringBuffer();

		query.append(" UPDATE POST ( 		");
		query.append(" SET SUBJECT = ?      ");
		query.append("		, CONTENT = ?   ");
		query.append(" WHERE POST_ID = ?    ");

		jdbcTemplate.update(query.toString()
				, post.getSubject()
				, post.getContent()
				, post.getPostId()); 

		return this.selectOnePost((int)post.getPostId());
	}

	@Override
	public void deleteOnePost(int postId) {
		
		StringBuffer query = new StringBuffer();

		query.append(" DELETE  				");
		query.append(" FROM POST       		");
		query.append(" WHERE POST_ID = ?    ");
		
		jdbcTemplate.update(query.toString(), postId); 

	}

}
