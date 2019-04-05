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
		
		//command�� �����Ͱ� �Ѿ��
		MemCommand mem = (MemCommand)command;
		
		//�ֹι�ȣ �˻��ؼ� ���̵� �ߺ� ���� �˻�
		if(page==0){
			
			//����ڰ� �Է��� �ֹ��� ������ �̹� ���ԵǾ� �ִ°�
			if(mem.getJumin().equals("1234")){
				String str = mem.getName() + "�� �̹� ���� �Ǿ��ֽ��ϴ�.";
			
				//errors��ü�� ������ ������ ������
				//�޼��� ���� ������ ������������ �� �Ѿ
				errors.rejectValue("message", str);
				
				mem.setMessage(str);
			}
			
		}else if(page==1){
			
			//1���������� �� �ڵ�
			
		}else{
			
			
		}
		
		super.postProcessPage(request, command, errors, page);
	}

	

	//mem3���� _cancle�� �������� (���������)
	@Override
	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		return new ModelAndView("test2/mem_cancel",errors.getModel());			
	}
	

	//mem3���� finish�� �������� (ȸ������ �Ϸ�������)
	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		

		//������û�� ó��(�Է��� �������϶� ������ �۾�)	
		
		//ȸ������
		
		return new ModelAndView("test2/mem_ok");
	}

	
	
	//Ư��â�� �߱����� �����ؾ� �� �ڵ�
	@Override
	protected Map<String,List<String>> referenceData(HttpServletRequest request, int page)
			throws Exception {
		
		//mem2(page�� 1�϶�)���� ȸ�������� �ɼ��ֱ�
		if(page==1){
			List<String> types = new ArrayList<String>();
			types.add("�Ϲ�ȸ��");
			types.add("���ȸ��");
			types.add("Ư��ȸ��");
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			
			map.put("types", types);
			
			return map;
		}
		
		return null;
	}
}
