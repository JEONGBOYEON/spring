package com.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

public class ListController extends AbstractCommandController{
	
	
	//생성자
	public ListController() {
		
		//dto객체생성(ListCommand)
		//listCommand = new ListCommand()
		setCommandClass(ListCommand.class);
		setCommandName("listCommand");
		
		
	}

	@Override
	protected ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException arg)
			throws Exception {
		
		ListCommand dto = (ListCommand)command;
		
		String message = "이름: " + dto.getUserName();
		message += ",아이디: " + dto.getUserId();
		
		request.setAttribute("message", message);
		
		return new ModelAndView("test/write_ok");
	}

}
