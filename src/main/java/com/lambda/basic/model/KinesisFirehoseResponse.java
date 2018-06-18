package com.lambda.basic.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class KinesisFirehoseResponse {

	/**
     * The record was transformed successfully.
     **/
	public static String TRANSFORMED_STATE_OK = "Ok";

    /**
     * The record was dropped intentionally by your processing logic.
     **/
    public static String TRANSFORMED_STATE_DROPPED = "Dropped";

    /**
     * The record could not be transformed.
     **/
    public static String TRANSFORMED_STATE_PROCESSINGFAILED = "ProcessingFailed";
    
    public List<FirehoseRecord> records = new ArrayList<FirehoseRecord>();
    
    public class FirehoseRecord {
		public String recordId;
		public String result = TRANSFORMED_STATE_OK;
		public String data;
		
		/**
	     * Base64 encodes the unencodedData and sets the data property.
	     **/
	     public void encodeAndSetData(String unencodedData)
	     {
	         data = Base64.getEncoder().encodeToString(unencodedData.getBytes());
	     }
	
	     public void encodeAndSetData(byte[] unencodedData)
	     {
	         data = Base64.getEncoder().encodeToString(unencodedData);
	     }
    }
}
