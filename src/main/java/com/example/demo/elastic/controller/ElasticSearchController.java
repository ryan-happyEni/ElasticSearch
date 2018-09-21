package com.example.demo.elastic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.elastic.client.ElasticClient;

@Controller
@RequestMapping("/elastic")
public class ElasticSearchController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${elastic.host}") 
    String host;

    @Value("${elastic.port}") 
    int port;

	@RequestMapping(value = {"", "/"})
	public String main() {
		return "elastic/elastic";
	}

    @RequestMapping(value="/search/{index}/{type}", method= {RequestMethod.POST} )
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> search(Locale locale, Model model, HttpSession session, HttpServletResponse response 
    		, @PathVariable("index") String index 
    		, @PathVariable("type") String type 
    		, @RequestBody Map<String, Object> data
    		) {
    	Map<String, Object> resultMap = null;
    	try {
    		Map<String, List<String>> searchWordMap = new HashMap<String, List<String>>();
    		if(data!=null) {
    			Set<String> keys = data.keySet();
    			for(String key : keys) {
    				List<String> list = new ArrayList<String>();
    				searchWordMap.put(key, list);
    				
    				String[] values = (String[])data.get(key);
    				for(String value : values) {
    					list.add(value);
    				}
    			}
    		}
    		ElasticClient elApi = new ElasticClient(host, port);
    		elApi.connect();
    		resultMap = elApi.sendSearch(index, type, searchWordMap);
    		elApi.disconnect();
    	}catch(Exception e) {
    		resultMap = new HashMap<String, Object>();
    		resultMap.put("error_code", -1);
    		resultMap.put("error_result", e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );

        return resultMap;
    }

    @RequestMapping(value="/get/{index}/{type}/{id}", method= {RequestMethod.POST} )
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> get(Locale locale, Model model, HttpSession session, HttpServletResponse response 
    		, @PathVariable("index") String index 
    		, @PathVariable("type") String type 
    		, @PathVariable("id") String id
    		) {
    	Map<String, Object> resultMap = null;
    	try {
    		ElasticClient elApi = new ElasticClient(host, port);
    		elApi.connect();
    		resultMap = elApi.sendGet(index, type, id, null);
    		elApi.disconnect();
    	}catch(Exception e) {
    		resultMap = new HashMap<String, Object>();
    		resultMap.put("error_code", -1);
    		resultMap.put("error_result", e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );

        return resultMap;
    }

    @RequestMapping(value="/put/{index}/{type}/{id}", method= {RequestMethod.POST} )
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody Map<String, Object> put(Locale locale, Model model, HttpSession session, HttpServletResponse response 
    		, @PathVariable("index") String index 
    		, @PathVariable("type") String type 
    		, @PathVariable("id") String id
    		, @RequestBody Map<String, Object> data
    		) {
    	Map<String, Object> resultMap = null;
    	try {
    		ElasticClient elApi = new ElasticClient(host, port);
    		elApi.connect();
    		resultMap = elApi.sendPut(index, type, id, data);
    		elApi.disconnect();
    	}catch(Exception e) {
    		resultMap = new HashMap<String, Object>();
    		resultMap.put("error_code", -1);
    		resultMap.put("error_result", e.toString());
    	}

        response.setStatus( HttpServletResponse.SC_OK  );

        return resultMap;
    }
}
