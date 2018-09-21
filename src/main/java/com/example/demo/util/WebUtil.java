package com.example.demo.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public class WebUtil {
    private Monitor logger = new Monitor();  
    private CommonUtil cutil;

    public WebUtil(){
    	cutil = new CommonUtil();
    }

    /**
     * multipart request check 
     * @param request - HttpServletRequest
     * @return - MultipartRequest true, not false
     */
    public boolean isMultipartRequest(HttpServletRequest request) {
    	logger.debug("Content-Type: " + request.getHeader("Content-Type"));
        String contentType = request.getHeader("Content-Type");

        return (contentType != null && contentType.indexOf("multipart/form-data") != -1);
    }   

    public HashMap<String, Object> getRequestValue(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
    	if(isMultipartRequest(request)){
    		MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
    		map = getRequestForm(mpRequest);
    	}else{
    		map = getRequestForm(request);
    	}
    	return map;
    }

    public HashMap<String, Object> getRequestForm(HttpServletRequest req) {
        return getRequestForm(true, req);
    }  

    public HashMap<String, Object> getRequestForm(boolean removeXSS, HttpServletRequest req) {
        String param = "";
        String[] values;
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
        	//map.put("servlet_call_url", req.getRequestURL());
        	map.put("servlet_call_url", req.getServletPath());
            Enumeration paramNames = req.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    param = (String) paramNames.nextElement();
                    values = req.getParameterValues(param);

                    if (values != null) {
                        if (values.length == 1)
                            map.put(param, removeXSS(removeXSS, values[0]));
                        else if (values.length > 1)
                            map.put(param, removeXSS(removeXSS, values));
                        else if (values.length < 1)
                            map.put(param, "");
                    } else
                        map.put(param, "");
                }
            } 
            addSessionInfoAll(req, map);

            //readParams(map);
        } catch (Exception ex) {
        	logger.error(ex, " CommonUtil getFormValue() Error occurred while process action: " + ex.getMessage());
        }
        return map;
    }  

    public String removeXSS(boolean removeXSS, String value){
    	return cutil.escapeXml(removeXSS, value);
    }
    
    public String[] removeXSS(boolean removeXSS, String[] values){
    	String[] re_values = {""};
    	if(values!=null){
    		re_values = new String[values.length];
    		for(int i=0; i<re_values.length; i++){
    			if(values[i]!=null){
					re_values[i] = cutil.escapeXml(removeXSS, values[i]);
    			}else{
    				re_values[i] = "";
    			}
    		}
    	}
    	
    	return re_values;
    }
    
    public void addSessionInfoAll(HttpServletRequest request, HashMap<String, Object> map){
    	Map<String, String> sessionUser = new HashMap<String, String>();

    	HttpSession session = request.getSession();
    	Enumeration enums = session.getAttributeNames();
    	String key="";
    	while(enums.hasMoreElements()){
    		key = (String)enums.nextElement();
        	map.put(key, session.getAttribute(key));
    	}
    }

    public void addSessionInfo(HttpServletRequest req, HashMap<String, Object> map) { 
        try {
            if(map!=null){
            	HttpSession session = req.getSession();
            	map.put("session_user_id", session.getAttribute("user_id"));
            	map.put("session_user_name", session.getAttribute("user_name"));
            	map.put("session_team_code", session.getAttribute("team_code")); 
            	map.put("session_team_name", session.getAttribute("team_name")); 
            }
        } catch (Exception ex) {
        	logger.error(ex, " CommonUtil addSessionInfo() Error occurred while process action: " + ex.getMessage());
        } 
    } 

    public void readParams(HashMap<String, Object> map) {
        if (map != null) {
            Set<String> keys = map.keySet();
            Object[] objs = keys.toArray();
        	StringBuffer strBuf = new StringBuffer();
        	strBuf.append("#####################################");
        	strBuf.append("## Request parameter{################");
            for (int i = 0; i < objs.length; i++) {
            	strBuf.append("## ").append(objs[i]).append(":").append(map.get(objs[i]));
            }
            strBuf.append("## }#################################");
            strBuf.append("#####################################");
            logger.debug(strBuf.toString());
        }
    } 
    
    public void printRequestParams(HttpServletRequest req) {
        String param = "";
        String[] values;
        try {
        	StringBuffer strBuf = new StringBuffer();
        	strBuf.append("\n#############################################################");
        	strBuf.append("\n## Request parameter{################ ").append(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:dd")).append(" ###");
        	strBuf.append("\n## servlet_call_url :").append(req.getServletPath());
            Enumeration paramNames = req.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    param = (String) paramNames.nextElement();
                    values = req.getParameterValues(param);

                    if (values != null) {
                        if (values.length == 1)
                        	strBuf.append("\n## ").append(param).append(":").append(values[0]);
                        else if (values.length > 1)
                        	strBuf.append("\n## ").append(param).append(":").append(values);
                        else if (values.length < 1)
                        	strBuf.append("\n## ").append(param).append(":");
                    } else {
                    	strBuf.append("\n## ").append(param).append(":");
                    }
                }
            } 

        	HttpSession session = req.getSession();
        	Enumeration enums = session.getAttributeNames();
        	String key="";
        	while(enums.hasMoreElements()){
        		key = (String)enums.nextElement();
        		if(key.indexOf("RSA_WEB_Key")>-1){
        			strBuf.append("\n## SESSION > ").append(key).append(":").append(session.getAttribute(key).toString().substring(0, 10));
        		}else{
        			strBuf.append("\n## SESSION > ").append(key).append(":").append(session.getAttribute(key));
        		}
        	}
        	
        	strBuf.append("\n## }#################################");
        	strBuf.append("\n#############################################################");
        	logger.debug(strBuf.toString());
        } catch (Exception ex) {
        	logger.error(ex, " CommonUtil getFormValue() Error occurred while process action: " + ex.getMessage());
        }
    }
}
