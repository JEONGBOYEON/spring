package com.test2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

public class MemController extends AbstractWizardFormController {

	
	public MemController() {
		//MemCommand info = new MemCommand();
		setCommandClass(MemCommand.class);
		setCommandName("info");
	}
	

	//main?
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors errors, int page) throws Exception {
		
		//command로 데이터가 넘어옴
		MemCommand mem = (MemCommand)command;
		
		//주민번호 검사해서 아이디 중복 여부 검사
		if(page==0){
			
			//사용자가 입력한 주민이 있으면 이미 가입되어 있는것
			if(mem.getJumin().equals("1234")){
				String str = mem.getName() + "님 이미 가입 되어있습니다.";
			
				//errors객체가 에러를 가지고 있으면
				//메세지 값만 보내고 다음페이지로 못 넘어감
				errors.rejectValue("message", str);
				
				mem.setMessage(str);
			}
			
		}else if(page==1){
			
			//1페이지에서 할 코딩
			
		}else{
			
			
		}
		
		super.postProcessPage(request, command, errors, page);
	}

	

	//mem3에서 _cancle을 눌렀을때 (취소했을때)
	@Override
	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		return new ModelAndView("test2/mem_cancel",errors.getModel());			
	}
	

	//mem3에서 finish을 눌렀을때 (회원가입 완료했을때)
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		

		//최종요청을 처리(입력이 정상적일때 실행할 작업)	
		
		//회원가입
		
		return new ModelAndView("test2/mem_ok");
	}

	
	
	//특정창이 뜨기전에 실행해야 할 코드
	@Override
	protected Map<String,List<String>> referenceData(HttpServletRequest request, int page)
			throws Exception {
		
		//mem2(page가 1일때)에서 회원구분을 옵션주기
		if(page==1){
			List<String> types = new ArrayList<String>();
			types.add("일반회원");
			types.add("기업회원");
			types.add("특별회원");
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			map.put("types", types);
			
			return map;
		}
		
		return null;
	}
}
