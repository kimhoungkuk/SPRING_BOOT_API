package com.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//ctrl+shift+O
@RestController
public class IndexController {
	
	@GetMapping("/")
	public String helloIndex(){
		return "Hello Boot";
	}
	
}
