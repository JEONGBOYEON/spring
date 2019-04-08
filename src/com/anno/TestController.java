package com.anno;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("anno.testController")
public class TestController {


	//원래 dispatcher에서 write.action오면 Controller.java의 list매소드로 찾아가라고
	//명시를 따로 했는데 어노케이션은 여기서 바로 연결해준다.
	@RequestMapping(value="/demo/write.action",method=(RequestMethod.GET))
	public String write() throws Exception{
	//기본 매개변수는 없고 필요하면 적어주면 된다.
	//struts에서는 함수내에서 getParameter로 필요한 값 받아왔어야 하는데
	//spring은 매개변수로 받아올 수 있음
		
		return "anno/created";
		
	}
	
	//write매소드와 같은 주소로(write.action)으로 오더라도 GET,POST방식으로 오느냐에 따라 
	//다른 매소드가 호출된다.
	@RequestMapping(value="/demo/write.action",method=(RequestMethod.POST))
	public String write_ok(TestCommand command,HttpServletRequest request) throws Exception{
		
		
		//어노케이션은 따로 command 객체를 생성하지 않아도 매개변수로 받으면 바로 사용가능
		String message = "이름: " + command.getUserName();
		message += "아이디: " + command.getUserId();
		
		request.setAttribute("message", message);
		
		return "anno/result";
	}

	@RequestMapping(value="/demo/save.action",method={RequestMethod.POST,RequestMethod.GET})
	public String save(TestCommand command,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		if(command==null||command.getMode()==null||command.getMode().equals("")){
			return "anno/test";
		}
		
		String msg = "이름: " + command.getUserName() + "<br/>";
		msg += "아이디: " + command.getUserId() + "<br/>";
		
		request.setAttribute("message", msg);
		
		return "anno/result";
	}
	
	@RequestMapping(value="/demo/demo.action",method={RequestMethod.POST,RequestMethod.GET})
	public String save(String userId,String userName,String mode,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		if(mode==null||mode.equals("")){
			return "anno/demo";
		}
		
		String msg = "이름: " + userName + "<br/>";
		msg += "아이디: " + userId + "<br/>";
		
		request.setAttribute("message", msg);
		
		return "anno/result";
	}
	
}
