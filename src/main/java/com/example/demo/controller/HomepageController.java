package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

	@GetMapping("/home")
	public String showHomepage()
	{
		return "homepage";
	}
	
	@GetMapping("/activity1")
	public String showactivity1()
	{
		return "activity1";
	}
	
	@GetMapping("/activity2")
	public String showactivity2()
	{
		return "activity2";
	}

}
