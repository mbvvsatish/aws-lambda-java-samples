package com.lambda.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.KinesisFirehoseEvent;
import com.amazonaws.services.lambda.runtime.events.KinesisFirehoseEvent.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambda.basic.APIGatewayLambda.SalaryRangeForDesignation;
import com.lambda.basic.model.Employee;
import com.lambda.basic.model.KinesisFirehoseResponse;
import com.lambda.basic.model.KinesisFirehoseResponse.FirehoseRecord;

/**
 * @author smadhavarapu
 *
 */
public class KinesisFirehoseLambdaTransform {
		
	public KinesisFirehoseResponse handleRequest(KinesisFirehoseEvent firehoseEvent, Context context) throws IOException {
    	LambdaLogger log = context.getLogger();
    	KinesisFirehoseResponse response  = new KinesisFirehoseResponse();

    	log.log("Transforming kinesis records");
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	List<Record> eventRecords = firehoseEvent.getRecords();
    	log.log("Total Records : "+eventRecords.size());
    	eventRecords.stream().forEach(data -> {
        	try {
	    		
	    		int len = data.getData().remaining();
	            byte[] buffer = new byte[len];
	            data.getData().get(buffer);
	            
				String logStr = new String(buffer, "UTF-8");
				
	    		log.log("Record ID : "+data.getRecordId());
	    		log.log("log Data : "+logStr);
	    		
	    		Employee employee = objectMapper.readValue(logStr, Employee.class);
	    		SalaryRangeForDesignation sal = SalaryRangeForDesignation.valueOf(employee.getDesignation());
	    		//Perform some Transformation
	    		employee.setMinsalary(sal.getMinSalary());
	    		employee.setMaxsalary(sal.getMaxSalary());
	    		
	    		String transformed = objectMapper.writeValueAsString(employee);
	    		log.log("Transformed Request :"+transformed.toString());


	    		FirehoseRecord record = response.new FirehoseRecord();
	    		record.recordId = data.getRecordId();
	    		record.result = KinesisFirehoseResponse.TRANSFORMED_STATE_OK;
	    		record.encodeAndSetData(transformed);
	    		
	    		response.records.add(record);
	    		
	    	} catch (Exception e) {
	    		log.log(e.fillInStackTrace().toString());
			}
    	});
    	return response;
    }
	
}
