package com.kosta;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kosta.exceptions.ApiError;
import com.kosta.posts.model.Post;
import com.kosta.response.ApiDataResponse;
import com.kosta.response.ApiResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloBootApplicationTests {
	
	/*
	 * @Value = 시스템 환경 변수의 값을 Injection
	*/		
	@Value("${local.server.port}")
	private int port;
	
	//API 호출을 하기 위한 추상화 클래스
	private RestTemplate restTemplate;
	// API Header에 작성될 데이터를 셋팅하는 클래스
	private HttpHeaders header;
	
	// 객체 <->  JSON 파싱
	private Gson gson;
	
	/*
	 * @Test가 동작되기 전에 필요한 작업을 수행
	*/
	@Before
	public void setUp(){
		restTemplate = new RestTemplate();
		header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		
		gson = new Gson();
	}
	
	@Test
	public void createPostFail_Subject_Missing(){
		Post post = new Post();
		post.setContent("Content");

		/*		
	  		{
				"content":"Content"
			}
		*/
		String requestJson = gson.toJson(post);
		
		/*		
		 * Request Body 작성
		 */
		HttpEntity<String> entity = new HttpEntity<>(requestJson, header);
		
		ResponseEntity<String> reponse = 
				restTemplate.exchange(
						"http://localhost:8080/posts", //API URL
						HttpMethod.POST, //Request Method
						entity, //Request Header
						String.class //Reponse Type
				);
		
		/*
		  JSON
		  {
		  	"status":"Failed"
		   "error":"subject is requied"
		  }
		 */
		 String responseBody = reponse.getBody();
		 ApiResponse apiResponse = 
				 gson.fromJson(responseBody, ApiResponse.class);
		 assertTrue(apiResponse.getStatus().equals("Failed"));
		 ApiError apierror = apiResponse.getError();
		 assertTrue(apierror.getCode().equals("1000"));
		 assertTrue(apierror.getMessage().equals("Messing Require Paramter : subject is required"));
		 
	/*	 assertTrue(apiResponse.getStatus().equals("Failed"));
  	 	 System.out.println(apiResponse.getError());
		 assertTrue(apiResponse.getError().equals("subject is required"));*/
		 
	}
	
	@Test
	public void createPostFail_Content_Missing(){
		Post post = new Post();
		post.setSubject("Subject");

		/*		
	  		{
				"subject":"Subject"
			}
		*/
		String requestJson = gson.toJson(post);
		
		/*		
		 * Request Body 작성
		 */
		HttpEntity<String> entity = new HttpEntity<>(requestJson, header);
		
		ResponseEntity<String> reponse = 
				restTemplate.exchange(
						"http://localhost:8080/posts", //API URL
						HttpMethod.POST, //Request Method
						entity, //Request Header
						String.class //Reponse Type
				);
		
		/*
		  JSON
		  {
		  	"status":"Failed"
		   "error":"content is requied"
		  }
		 */
		 String responseBody = reponse.getBody();
		 ApiResponse apiResponse = 
				 gson.fromJson(responseBody, ApiResponse.class);
		 
		 assertTrue(apiResponse.getStatus().equals("Failed"));
		 ApiError apierror = apiResponse.getError();
		 assertTrue(apierror.getCode().equals("1000"));
		 assertTrue(apierror.getMessage().equals("Messing Require Paramter : content is required"));
/*		 assertTrue(apiResponse.getError().equals("content is required"));*/
		 
	}
	
	@Test
	public void createPostOk(){
		Post post = new Post();
		post.setSubject("Subject");
		post.setContent("Content");

		/*		
	  		{
	  			"subject":"Subject",
				"content":"Content"
			}
		*/
		String requestJson = gson.toJson(post);
		
		/*		
		 * Request Body 작성
		 */
		HttpEntity<String> entity = new HttpEntity<>(requestJson, header);
		
		ResponseEntity<String> reponse = 
				restTemplate.exchange(
						"http://localhost:8080/posts", //API URL
						HttpMethod.POST, //Request Method
						entity, //Request Header
						String.class //Reponse Type
				);
		
		/*
		  JSON
		  {
		  	"status":"OK",
		  	"data" : {
		  		"postId" : n,
		  		"subject" : "Subject",
		  		"content" : "Content"
		  	},
		    "error":""
		  }
		 */
		 String responseBody = reponse.getBody();
		 
		 /*
		  * responseBody -> ApiDataResponse<POST> Parsing
		  * import java.lang.reflect.Type;
		  * import com.google.common.reflect.TypeToken;
		  */
		 Type postGenericType = new TypeToken<ApiDataResponse<Post>>(){}.getType();
		 
		 ApiDataResponse<Post> apiResponse = 
				 gson.fromJson(responseBody, postGenericType);
		 
		 assertTrue(apiResponse.getStatus().equals("OK"));
/*		 assertTrue(apiResponse.getError().equals(""));
	*/	
		 
		 ApiError apierror = apiResponse.getError();
		 assertTrue(apierror.getCode().equals(""));
		 assertTrue(apierror.getMessage().equals(""));
		 
		 Post responsePost = apiResponse.getData();
		 assertTrue(responsePost != null);
		 assertTrue(responsePost.getSubject().equals("Subject"));
		 assertTrue(responsePost.getContent().equals("Content"));

	}

	
	@Test
	public void getAllPostOk(){
		/*		
		 * Request Body 작성
		 */
		HttpEntity<String> entity = new HttpEntity<>(header);
		
		ResponseEntity<String> reponse = 
				restTemplate.exchange(
						"http://localhost:8080/posts", //API URL
						HttpMethod.GET, //Request Method
						entity, //Request Header
						String.class //Reponse Type
				);
		
		/*
		  JSON
		  {
		  	"status":"OK",
		  	"data" : [{
		  		"postId" : n,
		  		"subject" : "Subject",
		  		"content" : "Content"
		  	},{
		  		"postId" : n,
		  		"subject" : "Subject",
		  		"content" : "Content"
		  	}],
		    "error":""
		  }
		 */
		 String responseBody = reponse.getBody();
		 
		 /*
		  * responseBody -> ApiDataResponse<POST> Parsing
		  * import java.lang.reflect.Type;
		  * import com.google.common.reflect.TypeToken;
		  */
		 Type postGenericType = new TypeToken<ApiDataResponse<List<Post>>>(){}.getType();
		 
		 ApiDataResponse<List<Post>> apiResponse = 
				 gson.fromJson(responseBody, postGenericType);
		 
		 assertTrue(apiResponse.getStatus().equals("OK"));
		 
		 ApiError apierror = apiResponse.getError();
		 assertTrue(apierror.getCode().equals(""));
		 assertTrue(apierror.getMessage().equals(""));
		 
		 /*assertTrue(apiResponse.getError().equals(""));
		 */
		 
		 List<Post> responsePost = apiResponse.getData();
		 assertTrue(responsePost != null);
		 assertTrue(responsePost.size() >0);

	}
	
}
