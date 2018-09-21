package com.example.demo.elastic.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ElasticClient {
	private String host;
	private int port;
	private String scheme;
	private RestHighLevelClient client = null;
	private boolean isConnect=false;
	public ElasticClient(String host, int port) {
		this.host = host;
		this.port = port;
		this.scheme = "http";
	}
	
	public RestHighLevelClient connect() {
		try {
    		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, scheme));
    		builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
    		    @Override
    		    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
    		        return requestConfigBuilder.setConnectTimeout(300*1000).setSocketTimeout(300*1000).setConnectionRequestTimeout(300*1000); 
    		    }
    		});
    		
    		client = new RestHighLevelClient(builder);
    		isConnect=true;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return client;
	}
	
	public void disconnect() {
		try {
    		if(client!=null) {
    			client.close();
    		}

    		isConnect=false;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public boolean isConnected() {
		return isConnect && client!=null ? true : false;
	}

	
	public Map<String, Object> sendGet(String index, String type, String id, Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		if(isConnected()) {
	            ObjectMapper oMapper = new ObjectMapper();
	    		Map<?, ?> map = oMapper.convertValue(obj, Map.class);
	    	    GetRequest request = new GetRequest(index, type, id);
	
	            GetResponse response = client.get(request, RequestOptions.DEFAULT);
	
	            resultMap = response.getSource();
    		}else {
        		resultMap.put("error_code", -2);
        		resultMap.put("result", "not connected.");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		resultMap.put("error_code", -1);
    		resultMap.put("result", e.toString());
    	}
    	return resultMap;
	}
	
	public Map<String, Object> sendPut(String index, String type, String id, Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		if(isConnected()) {
	            ObjectMapper oMapper = new ObjectMapper();
	    		Map<?, ?> map = oMapper.convertValue(obj, Map.class);
	            IndexRequest request = new IndexRequest(index, type, id).source(map);
	            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
	
	    		resultMap.put("error_code", 0);
	    		resultMap.put("result_code", response.getResult());
	    		resultMap.put("result", response.toString());
    		}else {
        		resultMap.put("error_code", -2);
        		resultMap.put("result", "not connected.");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		resultMap.put("error_code", -1);
    		resultMap.put("result", e.toString());
    	}
    	return resultMap;
	}
	
	public Map<String, Object> sendUpdate(String index, String type, String id, Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		if(isConnected()) {
	            ObjectMapper oMapper = new ObjectMapper();
	    		Map<?, ?> map = oMapper.convertValue(obj, Map.class);
	    		UpdateRequest request = new UpdateRequest(index, type, id);
	    		request.doc(map);
	
	            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
	
	    		resultMap.put("error_code", 0);
	    		resultMap.put("result_code", response.getResult());
	    		resultMap.put("result", response.toString());
    		}else {
        		resultMap.put("error_code", -2);
        		resultMap.put("result", "not connected.");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		resultMap.put("error_code", -1);
    		resultMap.put("result", e.toString());
    	}
    	return resultMap;
	}
	
	public Map<String, Object> sendDelete(String index, String type, String id, Object obj) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		if(isConnected()) {
	            ObjectMapper oMapper = new ObjectMapper();
	    		Map<?, ?> map = oMapper.convertValue(obj, Map.class);
	    		DeleteRequest request = new DeleteRequest(index, type, id);
	
	    		DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
	
	    		resultMap.put("error_code", 0);
	    		resultMap.put("result_code", response.getResult());
	    		resultMap.put("result", response.toString());
    		}else {
        		resultMap.put("error_code", -2);
        		resultMap.put("result", "not connected.");
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		resultMap.put("error_code", -1);
    		resultMap.put("result", e.toString());
    	}
    	return resultMap;
	}
}
