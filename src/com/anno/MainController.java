package com.anno;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//������ �̷��� value�� method�� ���� ���µ� �Ϲ������� ���ļ� ����.
@Controller
@RequestMapping(value="/ma.action")
public class MainController {

	@RequestMapping(method=RequestMethod.GET)
	public String method(){
		
		
		return "/main";
	}
}
