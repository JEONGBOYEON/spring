package com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//demo/guest¸¦ ¶ç¿ï °Í

@Controller("demo.guestController")
public class GuestController {
	@RequestMapping(value="/demo/guest/guest.action",method={RequestMethod.POST,RequestMethod.GET})
	public String guest() throws Exception{
		
		return "guest.guestLayout";
	}
}
