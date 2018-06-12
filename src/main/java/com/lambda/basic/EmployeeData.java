package com.lambda.basic;

import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambda.basic.model.Employee;

public class EmployeeData {

	public void employeeDataHandler(Employee emp, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		String queueUrl = sqs.getQueueUrl("trainingtest").getQueueUrl();
		
		logger.log("Employee Id : "+emp.getEmpId());
		logger.log("Employee Name : "+emp.getEmpName());
		logger.log("Employee Department : "+emp.getDepartment());
		
		ObjectMapper objMapper = new ObjectMapper();
		String empJson = objMapper.writeValueAsString(emp);
		
		SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(empJson);
		
        sqs.sendMessage(send_msg_request);
		
	}
}
