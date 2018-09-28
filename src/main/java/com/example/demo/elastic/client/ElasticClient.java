package com.example.demo.elastic.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

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


	public SearchRequest formSearchRequestForMultiSearch(String index, String type, String field, String word) {
	    SearchRequest searchRequest = new SearchRequest(index);
	    searchRequest.types(type);
	    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    searchSourceBuilder.query(QueryBuilders.wildcardQuery(field, word));
	    searchRequest.source(searchSourceBuilder);
	    return searchRequest;
	}


	public Map<String, Object> sendSearch(String indexs, String types) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
		    resultMap.put("datas", new LinkedList<Map<String, Object>>());
    		if(isConnected()) {
				if(indexs==null || indexs.trim().equals("")) {
					indexs="_all";
				}
				
    			if(types!=null && !types.trim().equals("")) {
    				SearchRequest searchRequest = new SearchRequest(indexs); 
    				searchRequest.types(types);
    				
    				SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
    					
					resultMap.put("datas", response.toString());
    			}
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
	
	public Map<String, Object> sendSearch(String indexs, String types, String field, String value, int from, int size, boolean isFuzziness) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
		    resultMap.put("datas", new LinkedList<Map<String, Object>>());
    		if(isConnected()) {
				if(indexs==null || indexs.trim().equals("")) {
					indexs="_all";
				}
				
    			if(types!=null && !types.trim().equals("") && value!=null && !value.trim().equals("")) {
    				
    				StringBuilder script = new StringBuilder();
    				script.append("{");
    				script.append(" \"size\" : \"{{size}}\", ");
    				script.append(" \"from\" : \"{{from}}\", ");
    				script.append(" \"query\": { ");
    				script.append(" 	\"match\": { ");
    				if(isFuzziness) {
        				script.append(" 		\"{{field}}\": { ");
        				script.append(" 			\"query\" : \"{{value}}\", ");
        				script.append(" 			\"fuzziness\" : \"AUTO\" ");
        				script.append(" 		} ");
    					
    				}else {
        				script.append(" 		\"{{field}}\" : \"{{value}}\" ");
    				}
    				script.append(" 	} ");
    				script.append(" } ");
    				script.append("}");
    				
    				SearchTemplateRequest searchRequest = new SearchTemplateRequest();
    				searchRequest.setRequest(new SearchRequest(indexs)); 
    				searchRequest.setScriptType(ScriptType.INLINE);
    				searchRequest.setScript(script.toString());

    				Map<String, Object> scriptParams = new HashMap<>();
    				scriptParams.put("field", field);
    				scriptParams.put("value", value);
    				scriptParams.put("size", size);
    				scriptParams.put("from", from);
    				searchRequest.setScriptParams(scriptParams);
    				
    				SearchTemplateResponse response = client.searchTemplate(searchRequest, RequestOptions.DEFAULT);

    				List<SearchHit> sourceList = new ArrayList<SearchHit>();
    			    for (SearchHit hit : response.getResponse().getHits()) {
    			    	sourceList.add(hit);
    			    }

					resultMap.put("datas", sourceList);
    			}
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
	
	public Map<String, Object> sendSearch(String indexs, String types, String query_string) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
		    resultMap.put("datas", new LinkedList<Map<String, Object>>());
    		if(isConnected()) {
				if(indexs==null || indexs.trim().equals("")) {
					indexs="_all";
				}
				
    			if(types!=null && !types.trim().equals("") && query_string!=null && !query_string.trim().equals("")) {
    				StringBuilder script = new StringBuilder();
    				script.append(query_string);
    				
    				SearchTemplateRequest searchRequest = new SearchTemplateRequest();
    				searchRequest.setRequest(new SearchRequest(indexs)); 
    				searchRequest.setScriptType(ScriptType.INLINE);
    				searchRequest.setScript(script.toString());

    				Map<String, Object> scriptParams = new HashMap<>();
    				searchRequest.setScriptParams(scriptParams);
    				
    				SearchTemplateResponse response = client.searchTemplate(searchRequest, RequestOptions.DEFAULT);

    				List<SearchHit> sourceList = new ArrayList<SearchHit>();
    			    for (SearchHit hit : response.getResponse().getHits()) {
    			    	sourceList.add(hit);
    			    }

					resultMap.put("datas", sourceList);
    			}
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

	
	public Map<String, Object> sendMultiSearch(String index, String type, Map<String, List<String>> searchWordMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
		    resultMap.put("datas", new LinkedList<Map<String, Object>>());
    		if(isConnected()) {
    			if(type!=null && !type.trim().equals("") && searchWordMap!=null) {
    				
    				Set<String> keys = searchWordMap.keySet();
    				if(keys.size()>0) {
    					/*
	    				MultiSearchRequest request = new MultiSearchRequest();

	    				for(String key : keys) {
	    					for(String word: searchWordMap.get(key)) {
	    		    		    request.add(formSearchRequestForMultiSearch(index, type, key, word));
	    					}
	    				}

	    			    MultiSearchResponse searchResponse = client.multiSearch(request, RequestOptions.DEFAULT);
	    			    List<Map<String, Object>> datas = (LinkedList<Map<String, Object>>)resultMap.get("datas");
	    			    for (int i = 0; i < searchResponse.getResponses().length; i++) {
	    			        SearchHit[] hits = searchResponse.getResponses()[i].getResponse().getHits().getHits();
	    			        for (SearchHit hit : hits) {
	    			        	datas.add(hit.getSourceAsMap());
	    			        }
	    			    }
	    			    */
    				}
    			}

			    
			    MultiSearchRequest request = new MultiSearchRequest();    
			    SearchRequest firstSearchRequest = new SearchRequest();   
			    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			    searchSourceBuilder.query(QueryBuilders.matchQuery("name", "Test"));
			    firstSearchRequest.source(searchSourceBuilder);
			    request.add(firstSearchRequest);                          
			    SearchRequest secondSearchRequest = new SearchRequest();  
			    searchSourceBuilder = new SearchSourceBuilder();
			    searchSourceBuilder.query(QueryBuilders.matchQuery("user", "123"));
			    secondSearchRequest.source(searchSourceBuilder);
			    request.add(secondSearchRequest);
			    
			    SearchRequest searchRequest = new SearchRequest(index); 
			    searchRequest.types(type);
			    
			    MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
			    
			    resultMap.put("datas", response.toString());
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
