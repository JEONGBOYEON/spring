package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//WEB-INF/main.jsp�� ��� ��

//�̹� MainController��� �Ȱ��� �̸��� Ŭ������ �̸��� ���� �ʰ� ��Ʈ�ѷ� ��ü�� �������־
//�̰͵� �̸��� ���� ��ü�� ����� �浹����.
@Controller("demo.mainController")
public class MainController {
	
	@RequestMapping(value="/main.action",method={RequestMethod.POST,RequestMethod.GET})
	public String method(){
		
		
		
		return "mainLayout";
	}
	
}
