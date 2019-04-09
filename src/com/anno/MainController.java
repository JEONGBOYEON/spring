package com.anno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//원래는 이렇게 value랑 method를 따로 쓰는데 일반적으로 합쳐서 쓴다.
@Controller
@RequestMapping(value="/ma.action")
public class MainController {

	@RequestMapping(method=RequestMethod.GET)
	public String method(){
		
		
		return "/main";
	}
}
