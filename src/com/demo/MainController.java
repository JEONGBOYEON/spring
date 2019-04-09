package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//WEB-INF/main.jsp를 띄울 것

//이미 MainController라는 똑같은 이름의 클래스에 이름을 주지 않고 컨트롤러 객체를 생성해주어서
//이것도 이름이 없이 객체를 만들면 충돌난다.
@Controller("demo.mainController")
public class MainController {
	
	@RequestMapping(value="/main.action",method={RequestMethod.POST,RequestMethod.GET})
	public String method(){
		
		
		
		return "mainLayout";
	}
	
}
