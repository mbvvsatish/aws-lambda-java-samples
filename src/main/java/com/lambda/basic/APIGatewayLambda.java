package com.lambda.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambda.basic.model.Employee;

public class APIGatewayLambda {
		
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayEvent, Context context) throws IOException {
    	LambdaLogger log = context.getLogger();
    	APIGatewayProxyResponseEvent response  = new APIGatewayProxyResponseEvent();
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	try {
        	String json = apiGatewayEvent.getBody();
        	log.log("Source Payload : "+json);
        	String urlpath = apiGatewayEvent.getResource();
        	log.log("URL Path : "+urlpath);
        	
    		
    		Employee employee = objectMapper.readValue(json, Employee.class);
    		SalaryRangeForDesignation sal = SalaryRangeForDesignation.valueOf(employee.getDesignation());
    		//Perform some Transformation
    		employee.setMinsalary(sal.getMinSalary());
    		employee.setMaxsalary(sal.getMaxSalary());
    		
    		String transformed = objectMapper.writeValueAsString(employee);
    		log.log("Transformed Request :"+transformed.toString());

    		// Firehose client
    		//AmazonKinesisFirehose firehoseClient = AmazonKinesisFirehoseClientBuilder.standard().build();
    		//PutRecordRequest putRecordRequest = new PutRecordRequest();
    		//String deliveryStreamName = System.getProperty("DELIVERY_STREAM_NAME", "click-events-test");
            //putRecordRequest.setDeliveryStreamName(deliveryStreamName);
    		//log.log("Kinesis firehose delivery stream name : "+deliveryStreamName);
            //Record record = new Record().withData(ByteBuffer.wrap(transformed.getBytes()));
            //putRecordRequest.setRecord(record);

            // Put record into the DeliveryStream
            //firehoseClient.putRecord(putRecordRequest);    		
    		
    		response.setBody(transformed);
    		response.setStatusCode(200);
    		
    	} catch (Exception e) {
			log.log(e.fillInStackTrace().toString());
		}

    	return response;
    }
	
	public enum SalaryRangeForDesignation {
		SoftwareEngineer(45000.0, 90000.0),
		TeamLead(75000.0, 120000.0),
		Manager(90000.0, 150000.0),
		Director(120000.0, 175000),
		VP(150000.0, 225000.0);
		
		double minSalary;
		double maxSalary;
		private SalaryRangeForDesignation(double minSalary, double maxSalary) {
			this.minSalary = minSalary;
			this.maxSalary = maxSalary;
		}
		
		public double getMinSalary() {
			return minSalary;
		}
		
		public double getMaxSalary() {
			return maxSalary;
		}
	}

}
