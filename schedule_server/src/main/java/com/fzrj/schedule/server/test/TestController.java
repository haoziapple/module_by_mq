package com.fzrj.schedule.server.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fzrj.schedule.service.SpringContextUtil;

@Controller
@RequestMapping("/test")
public class TestController
{
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void testSpring() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object springBean = SpringContextUtil.getBean("methodSpringBean");
		Method m = springBean.getClass().getDeclaredMethod("testMethod");
		m.invoke(springBean);
	}
}
