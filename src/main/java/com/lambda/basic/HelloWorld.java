package com.lambda.basic;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.lambda.basic.model.Hello;

public class HelloWorld {
	
	public void handleRequest(Hello name, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		
		logger.log("Hello "+name.getName()+"!");
		
	}
}
