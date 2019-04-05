package com.test3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class MultiTestController extends MultiActionController{
/*
	//struts의 excute같은 매소드 오버라이드 안해주고 원하는 이름으로 바꾸고 사용
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		return super.handleRequestInternal(arg0, arg1);
	}
*/

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("message", "list 페이지");
		
		return new ModelAndView("test3/testList");
		
	}
	
	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("message", "view 페이지");
		
		return new ModelAndView("test3/testView");
		
	}
}
