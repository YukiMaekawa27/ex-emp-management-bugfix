package jp.co.sample.emp_management.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorFourOFour implements ErrorController {
	
	private static final String PATH = "/error";
	
	@Override
	@RequestMapping(PATH)
	public String getErrorPath() {
		return "error/notFound";
	}

}
