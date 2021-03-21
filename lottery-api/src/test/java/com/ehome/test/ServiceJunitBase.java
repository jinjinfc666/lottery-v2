package com.ehome.test;


import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.meterware.servletunit.ServletTestCase;

public class ServiceJunitBase extends ServletTestCase {
	
	protected ApplicationContext ctx = null;
	
	public ServiceJunitBase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		// 通过代码设置并启动一个服务器，该服务器是servlet的测试容器
		super.setUp();
		
//		String contextFilePath = File.separator + File.separator + "home"+File.separator +"nick"+File.separator +"workspace"+File.separator +"lottery-v2"+File.separator +"lottery-api"+File.separator +"src"+File.separator +"main"+File.separator +"resources"+File.separator +"applicationContext.xml";
		String contextFilePath = "E:\\workspace\\lottery-v2\\lottery-api\\src\\main\\resources\\applicationContext.xml";
		System.out.println(new File(contextFilePath).exists());
		String[] paths = {contextFilePath};
		ctx = new FileSystemXmlApplicationContext(paths);
	}

	@Override
	protected void tearDown() throws Exception {
		
	}
}