package jp.co.sample.emp_management.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorFiveOO implements HandlerExceptionResolver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorFiveOO.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		LOGGER.error("システムエラーが発生しました！", ex);
		return new ModelAndView("redirect:/maintenance");
	}

}
