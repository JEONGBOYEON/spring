package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//demo/bbs¸¦ ¶ç¿ï °Í

@Controller("demo.boardControlelr")
public class BoardController {
	
	@RequestMapping(value="/demo/bbs/list.action",method={RequestMethod.POST,RequestMethod.GET})
	public String list() throws Exception{
		
		return "list.bbsLayout";
	}
	
	@RequestMapping(value="/demo/bbs/write.action",method={RequestMethod.POST,RequestMethod.GET})
	public String write() throws Exception{
		
		return "write.bbsLayout";
	}
	
}
