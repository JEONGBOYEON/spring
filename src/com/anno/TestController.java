package com.anno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("anno.testController")
public class TestController {


	//���� dispatcher���� write.action���� Controller.java�� list�żҵ�� ã�ư����
	//��ø� ���� �ߴµ� ������̼��� ���⼭ �ٷ� �������ش�.
	@RequestMapping(value="/demo/write.action",method=(RequestMethod.GET))
	public String write() throws Exception{
	//�⺻ �Ű������� ���� �ʿ��ϸ� �����ָ� �ȴ�.
	//struts������ �Լ������� getParameter�� �ʿ��� �� �޾ƿԾ�� �ϴµ�
	//spring�� �Ű������� �޾ƿ� �� ����
		
		return "anno/created";
		
	}
	
	//write�żҵ�� ���� �ּҷ�(write.action)���� ������ GET,POST������� �����Ŀ� ���� 
	//�ٸ� �żҵ尡 ȣ��ȴ�.
	@RequestMapping(value="/demo/write.action",method=(RequestMethod.POST))
	public String write_ok(TestCommand command,HttpServletRequest request) throws Exception{
		
		
		//������̼��� ���� command ��ü�� �������� �ʾƵ� �Ű������� ������ �ٷ� ��밡��
		String message = "�̸�: " + command.getUserName();
		message += "���̵�: " + command.getUserId();
		
		request.setAttribute("message", message);
		
		return "anno/result";
	}

	@RequestMapping(value="/demo/save.action",method={RequestMethod.POST,RequestMethod.GET})
	public String save(TestCommand command,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		if(command==null||command.getMode()==null||command.getMode().equals("")){
			return "anno/test";
		}
		
		String msg = "�̸�: " + command.getUserName() + "<br/>";
		msg += "���̵�: " + command.getUserId() + "<br/>";
		
		request.setAttribute("message", msg);
		
		return "anno/result";
	}
	
	@RequestMapping(value="/demo/demo.action",method={RequestMethod.POST,RequestMethod.GET})
	public String save(String userId,String userName,String mode,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		if(mode==null||mode.equals("")){
			return "anno/demo";
		}
		
		String msg = "�̸�: " + userName + "<br/>";
		msg += "���̵�: " + userId + "<br/>";
		
		request.setAttribute("message", msg);
		
		return "anno/result";
	}
	
}
