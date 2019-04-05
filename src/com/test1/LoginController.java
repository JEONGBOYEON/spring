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
	
	//Ŭ���� ����Ҳ��� �� ������ �ݵ�� �����ϱ� ���� �����
	private Authenticator authenticator;
	
	//������ ����
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	
	//Submit��ư�� Ŭ���ϸ� �ڵ����� ����
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		LoginCommand login = (LoginCommand)command;
		
		try {
			
			//üũ�Ϸ� ����!
			//������ ���� userException���� ����
			authenticator.authen(login.getUserId(), login.getUserPwd());
			
			String message = "id: " +login.getUserId();
			message += ",pwd: " + login.getUserPwd();
			message += ",type: " + login.getLoginType();
			
			request.setAttribute("message", message);
			
			return new ModelAndView("test1/login_ok");
			
		} catch (Exception e) {
			//showFormd�� �������� ��ü�ε�
			//�Է�â�� �ٽ� �����ִ� ����
			return showForm(request, response, errors);
		}
		
	}

	
	//referenceData : article���� ������ư�� ������ ���� ������ �����͵��� ���ִµ�
	//�� �����͸� �������� �۾��� ���ִ� �κ�
	//���� ���ϴ� �۾��� ���� �̸� ���۾� �س��� �ʿ��Ҷ� ������ ��(�˾Ƽ� ������ ����)
	
	//�Է�â�� �������� Ư�������͸� �Է�â�� ������ ���(����â ��ﶧ ������ �����ϴ� �۾��� ���)
	@Override
	protected Map<String,List<String>> referenceData(HttpServletRequest request) throws Exception {
		
		List<String> loginTypes = new ArrayList<String>();

		loginTypes.add("�Ϲ�ȸ��");
		loginTypes.add("���ȸ��");
		loginTypes.add("Ư��ȸ��");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		map.put("loginTypes", loginTypes);
		
		return map;
	}
	
	
	

}
