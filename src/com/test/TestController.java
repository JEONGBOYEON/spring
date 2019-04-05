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
		
		/*ModelAndView : ��ȯ��
		  ���������� �ִ� ��ȯ��
		  model : ������ / view : jsp
		  ModelAndView : �����Ϳ� jsp�� �ѹ��� ��� ��ȯ 
		*/
		
		
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		
		String msg = "";
		
		if(hour>=6 && hour<=8){
			msg = "��ħ�̴� �Ͼ";
		}else if(hour>=8 && hour<=13){
			msg = "�����̴�";
		}else if(hour>=13 && hour<14){
			msg = "���ɽð��Դϴ�";
		}else if(hour>=14 && hour<18){
			msg = "�����ϰ� �ð��Դϴ�";
		}else{
			msg = "����սô�";
		}
		
		request.setAttribute("msg", msg);
		
		//struts���� Map�� ���� �־��µ� spring�� �׷��� ����
		//new ModelAndView�� �ٷ� ������
		//�տ� �ִ� �����ÿ� �ڿ��ִ� .jsp�� �׻� �Ȱ��Ƽ� ������ /test/view.jsp ��� �Ǵµ�
		//dispatcher-servlet.xml���� �����ÿ� .jsp�� �׻� �ٵ��� ȯ�漳�� ���༭ ������� ��!!
		return new ModelAndView("test/view");
		
	}

	
	
}
