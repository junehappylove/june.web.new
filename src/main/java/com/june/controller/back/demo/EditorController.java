package com.june.controller.back.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/editor")
public class EditorController {

	@RequestMapping("/")
	public ModelAndView init() {
		ModelAndView result = null;
		result = new ModelAndView("demo/kindeditor");
		return result;
	}

}
