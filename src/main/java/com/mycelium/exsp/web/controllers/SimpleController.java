package com.mycelium.exsp.web.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	ModelAndView getRootPage() {
		ModelAndView mav = new ModelAndView("root");
		mav.addObject("time", new Date());
		return mav;
	}
}
