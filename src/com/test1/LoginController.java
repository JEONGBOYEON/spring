package com.test1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController{
	
	//클래스 사용할꺼면 이 변수를 반드시 쓸꺼니까 셋터 만들기
	private Authenticator authenticator;
	
	//의존성 주입
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	
	//Submit버튼을 클릭하면 자동으로 실행
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		LoginCommand login = (LoginCommand)command;
		
		try {
			
			//체크하러 가라!
			//에러가 나면 userException으로 간다
			authenticator.authen(login.getUserId(), login.getUserPwd());
			
			String message = "id: " +login.getUserId();
			message += ",pwd: " + login.getUserPwd();
			message += ",type: " + login.getLoginType();
			
			request.setAttribute("message", message);
			
			return new ModelAndView("test1/login_ok");
			
		} catch (Exception e) {
			//showFormd은 스프링의 객체인데
			//입력창을 다시 보여주는 역할
			return showForm(request, response, errors);
		}
		
	}

	
	//referenceData : article에서 수정버튼을 누르면 원래 기존의 데이터들이 들어가있는데
	//그 데이터를 가져오는 작업을 해주는 부분
	//내가 원하는 작업을 위해 미리 선작업 해놓고 필요할때 가져다 씀(알아서 가져다 쓴다)
	
	//입력창을 띄우기전에 특정데이터를 입력창에 보낼때 사용(수정창 띄울때 데이터 생성하는 작업을 기술)
	@Override
	protected Map<String,List<String>> referenceData(HttpServletRequest request) throws Exception {
		
		List<String> loginTypes = new ArrayList<String>();

		loginTypes.add("일반회원");
		loginTypes.add("기업회원");
		loginTypes.add("특별회원");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		map.put("loginTypes", loginTypes);
		
		return map;
	}
	
	
	

}
