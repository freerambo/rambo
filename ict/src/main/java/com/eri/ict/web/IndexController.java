package com.eri.ict.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class IndexController {
	
	public String index() {
		return "redirect:/project/";
	}
}