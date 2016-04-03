package com.inteview.secbi;

import java.util.Arrays;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.inteview.secbi.constant.InputSource;
import com.inteview.secbi.manager.LogManager;

/**
 * 
 *
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(App.class, args);
		if (args == null || args.length < 2)
			throw new RuntimeException("Must insert type and name");
		String type = args[0];
		String name = args[1];

		if (!EnumUtils.isValidEnum(InputSource.class, type))
			throw new RuntimeException(
					"first argument (type) should be of type " + Arrays.asList(InputSource.values()));
		LogManager logManager = ctx.getBean(LogManager.class);
		logManager.parseLog(InputSource.valueOf(type), name);

	}
}
