package com.lambda.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3EventLambda {

	public void handleRequest(S3Event s3Event, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		
		logger.log("Execution Started");
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
													        .withRegion("us-east-1")
													        .build();
		
		
		s3Event.getRecords().stream().forEach(event -> {
			String bucketName = event.getS3().getBucket().getName();
			String objectKey = event.getS3().getObject().getKey();
			logger.log("Bucket Name :: "+bucketName);
			logger.log("Object Key :: "+objectKey);
			S3ObjectInputStream is = s3Client.getObject(bucketName, objectKey).getObjectContent();
			
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
			
			String line;
			try {
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				logger.log(sb.toString());
				
				br.close();
			} catch (IOException e) {
				logger.log(e.getMessage());
				e.printStackTrace();
			}
			
		});
		
	}

}
