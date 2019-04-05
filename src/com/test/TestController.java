package com.test;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class TestController extends AbstractController{

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		/*ModelAndView : 반환값
		  스프링에만 있는 반환값
		  model : 데이터 / view : jsp
		  ModelAndView : 데이터와 jsp를 한번에 묶어서 반환 
		*/
		
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		String msg = "";
		
		if(hour>=6 && hour<=8){
			msg = "아침이다 일어나";
		}else if(hour>=8 && hour<=13){
			msg = "지각이다";
		}else if(hour>=13 && hour<14){
			msg = "점심시간입니다";
		}else if(hour>=14 && hour<18){
			msg = "오후일과 시간입니다";
		}else{
			msg = "퇴근합시다";
		}
		
		request.setAttribute("msg", msg);
		
		//struts에는 Map이 따로 있었는데 spring은 그런게 없고
		//new ModelAndView로 바로 보내줌
		//앞에 있는 슬러시와 뒤에있는 .jsp는 항상 똑같아서 원래는 /test/view.jsp 써야 되는데
		//dispatcher-servlet.xml에서 슬러시와 .jsp를 항상 붙도록 환경설정 해줘서 지워줘야 함!!
		return new ModelAndView("test/view");
		
	}

	
	
}
