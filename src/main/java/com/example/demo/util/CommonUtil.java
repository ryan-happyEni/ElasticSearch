package com.example.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class CommonUtil {
	/**
	 * Null 체크
	 * @param obj : 객체
	 * @return
	 */
	public boolean isNull(Object obj){
		return isNull(obj, false);
	}

	/**
	 * 문자열이 빈문자열인지 체크
	 * @param obj : 문자열 객체
	 * @param whiteSpaceMode : 공백포함여부 (공백포함=true, 공백제외=false)
	 * @return
	 */
	public boolean isNull(Object obj, boolean whiteSpaceMode){
		boolean reMode = true;
		try{
			if(obj!=null){
				if(whiteSpaceMode){
					if(!obj.toString().equals("")) reMode = false; 
				}else reMode = false;
			} 
		}catch(Exception e){
			System.out.println(e);
		}
		return reMode;
	}

	/**
	 * 총길이 만큼 오른쪽으로 문자열 채우기
	 * @param str : 문자열
	 * @param pStr : 채울문자열
	 * @param len : 총길이
	 * @return 채워진 문자열 반환
	 */
    public String rpad(String str, String pStr, int len) {
        String reStr = "";
        str = toString(str);
        if (str.length() < len) {
            reStr = str;
            for (int i = str.length(); i < len; i++) {
                reStr += pStr;
            }
        } else if (str.length() > len) {
            reStr = str.substring(0, len);
        } else {
            reStr = str;
        }
        return reStr;
    }

    /**
	 * 총길이 만큼 오른쪽으로 문자열 채우기
	 * @param str : 문자열
	 * @param pStr : 채울문자열
	 * @param len : 총길이
	 * @return 채워진 문자열 반환
     */
    public String lpad(String str, String pStr, int len) {
        String reStr = "";
        str = toString(str);
        if (str.length() < len) {
            for (int i = 0; i < len - str.length(); i++) {
                reStr += pStr;
            }
            reStr += str;
        } else if (str.length() > len) {
            reStr = str.substring(0, len);
        } else {
            reStr = str;
        }
        return reStr;
    }
	 
    /**
     * long 값으로 변환
     * @param obj
     * @return
     */
	public long toLong(Object obj){
		long i=0; 
		if(obj!=null&&!obj.toString().trim().equals("")){  
			i = Long.parseLong(obj.toString()); 
		}
		return i;		
	} 
	 
	/**
	 * int 값으로 변환
	 * @param obj
	 * @return
	 */
	public int toInt(String obj){
		int i=0; 
		if(obj!=null&&!obj.trim().equals("")){  
			i = Integer.parseInt(obj); 
		}
		return i;		
	} 
	 
	/**
	 * int 값으로 변환
	 * @param obj
	 * @return
	 */
	public int toInt(Object obj){
		int i=0; 
		if(obj!=null&&!obj.toString().trim().equals("")){  
			i = Integer.parseInt(obj.toString()); 
		}
		return i;		
	} 
	 
	/**
	 * double 값으로 변환
	 * @param obj
	 * @return
	 */
	public double toDouble(String obj){
		double i=0.0; 
		if(obj!=null&&!obj.trim().equals("")){  
			i = Double.parseDouble(obj); 
		}
		return i;		
	} 
	 
	/**
	 * double 값으로 변환
	 * @param obj
	 * @return
	 */
	public double toDouble(Object obj){
		double i=0.0; 
		if(obj!=null&&!obj.toString().trim().equals("")){  
			i = Double.parseDouble(obj.toString()); 
		}
		return i;		
	} 
	 
	/**
	 * 문자열로 변환
	 * @param obj
	 * @return
	 */
	public String toString(Object obj){
		return toString(obj, -1);		
	} 
	 
	/**
	 * 문자열로 변환
	 * @param obj
	 * @param maxByte
	 * @return
	 */
	public String toString(Object obj, int maxByte){
		String str=""; 
		if(obj!=null){  
			str = obj.toString(); 
		}
		if(maxByte>-1){
			str = cutWordInByteLegnth(str, maxByte);
		}
		return str;		
	} 
	 
	/**
	 * 문자열로 변환
	 * @param obj : 객체
	 * @param sqlInjectionCheck : sql injection code 삭제 여부. true=삭제, false=그냥둠
	 * @return
	 */
	public String toString(Object obj, boolean sqlInjectionCheck){
		return toString(obj, sqlInjectionCheck, -1);		
	}
	 
	/**
	 * 문자열로 변환
	 * @param obj : 객체
	 * @param maxByte
	 * @param sqlInjectionCheck : sql injection code 삭제 여부. true=삭제, false=그냥둠
	 * @return
	 */
	public String toString(Object obj, boolean sqlInjectionCheck, int maxByte){
		String str=""; 
		if(obj!=null){  
			str = obj.toString(); 
			if(sqlInjectionCheck){
				str = removeSQLInjectionCode(str);
			}
		}
		if(maxByte>-1){
			str = cutWordInByteLegnth(str, maxByte);
		}
		return str;		
	}
	 
	/**
	 * 문자열로 변환
	 * @param obj : 객체
	 * @param orgCharSet : 인코딩
	 * @param reCharSet : 인코딩
	 * @return
	 * @throws Exception
	 */
	public String toString(Object obj, String orgCharSet, String reCharSet) throws Exception{
		String str=""; 
		if(obj!=null){  
			str = obj.toString();
			str = new String(str.getBytes(orgCharSet),reCharSet);
		}
		return str;		
	} 
	 
	/**
	 * 문자열 배열로 변환
	 * @param obj : 문자열 배열 객체
	 * @return
	 */
	public String[] toStringArray(Object obj){
		String[] strArr = null;
		try{ 
			if(obj!=null) strArr = (String[])obj;  
		}catch(Exception e){ 
			if(obj!=null) strArr = new String[]{obj.toString()}; 
		}
		return strArr;		
	} 

	/**
	 * 문자열 배열을 문자열 합치기
	 * @param strArr : 문자열 배열
	 * @return
	 */
    public String toConcatString(String[] strArr) {
        StringBuffer strBuf = new StringBuffer();
        if (strArr != null && strArr.length > 0) {
            int len = strArr.length;
            for (int i = 0; i < len; i++) {
                if (i > 0)
                    strBuf.append(",");
                strBuf.append("'").append(strArr[i]).append("'");
            }
        }
        return strBuf.toString();
    }
	 
    /**
     * 문자열 배열을 List 배열로 변환
     * @param strArr
     * @return
     */
	public List<String> toList(String[] strArr){ 
		List<String> list = Arrays.asList(strArr) ; 
		return list; 
	} 

    /**
     * 문자열을 구분자로 하여 List 로 반환
     * @param str : 문자열
     * @param gubun : 구분자
     * @param trimMode : trim 여부, true=trim
     * @return
     */
    public ArrayList<String> toList(String str, String gubun, boolean trimMode) {
    	ArrayList<String> list = new ArrayList<String>();
        if(str!=null&&gubun!=null){
        	StringTokenizer token = new StringTokenizer(str, gubun);
        	while(token.hasMoreTokens()){
        		if(trimMode) list.add(token.nextToken().trim());
        		else list.add(token.nextToken());
        	}
        }
        return list;
    }
    
	/**
	 * 문자열을 구분자로 하여 List 반환
	 * @param value
	 * @param deli
	 * @return
	 */
    public ArrayList<String> toStringArray(String value, String deli){
    	ArrayList<String> list = new ArrayList<String>();
    	/*
    	StringTokenizer token = new StringTokenizer(value, regpx);
    	while(token.hasMoreTokens()){
    		list.add(token.nextToken());
    	}
    	*/
    	if(deli!=null){
    		if(value.indexOf(deli)>-1){
    			String temp = value;
    			while(temp.indexOf(deli)>-1){
    				list.add(temp.substring(0, temp.indexOf(deli)));
    				temp = temp.substring(temp.indexOf(deli)+deli.length());
    			}
				list.add(temp);
    		}else{
    			list.add(value);
    		}
    	}
    	return list;
    }
    
    /**
     * Map 의 값을 문자열 배열로 반환
     * @param map : map 객체
     * @param orders : key배열
     * @return
     * @throws Exception
     */
    public String[] mapToStringArray(Map<String, Object> map, String[] keys) throws Exception{
    	String[] arr = new String[]{};
    	if(map!=null&&keys!=null){
    		arr = new String[keys.length];
    		int idx=0;
    		for(String key : keys){
    			arr[idx++] = ""+map.get(""+key);
    		}
    	}
    	return arr;
    }    
	
	/**
	 * 숫자포맷
	 * @param value : 숫자
	 * @param pattern : 포맷 { ###,###.### or ###.### or 0000000.000 or $###,###.### }
	 * @return
	 */
	public String toNumberFormat(String value, String pattern){
		String output = "0";
		if(value!=null&&!value.equals("")){
			DecimalFormat formatter = new DecimalFormat(pattern);
			output = formatter.format(Double.parseDouble(value));
		}
		return output;
	}
    
	/**
	 * 문자열을 자른후 특정 문자를 붙여서 반환
	 * @param str : 문자열
	 * @param maxlength : 최대길이
	 * @param appStr : 붙일 문자열
	 * @return 문자열을 자른후 특정 문자를 붙여서 반환
	 */
    public String cutStr(String str, int maxlength, String appStr){
    	if(str!=null&&str.length()>maxlength){
    		return str.substring(0, maxlength)+appStr;
    	}else return str;
    }
    
    /**
     * 반복 문자열 만들기 
     * @param str : 문자열
     * @param loop : 반복횟수
     * @return 반복된 문자열
     */
    public String instStr(String str, int loop){
    	StringBuilder strBuild = new StringBuilder();
    	for(int i=0; i<loop; i++){
    		strBuild.append(str);
    	}
    	return strBuild.toString();    	
    }
    
    /**
     * 문자열로된 숫자(정수) 차이값
     * @param strA : 정수
     * @param strB : 정수
     * @return strA-strB의 결과값
     */
    public int compare(String strA, String strB){
    	int a = strA!=null?Integer.parseInt(strA):0;
    	int b = strB!=null?Integer.parseInt(strB):0; 
    	return a-b;
    }
   
    /**
     * List 의 사이즈
     * @param list
     * @return
     */
    public int listSize(List list){
    	if(list!=null) return list.size();
    	else return 0;
    }
    
    /**
     * 날짜 변환
     * @param dateStr : 날짜 문자열
     * @param sourceFormat : dateStr의 문자열 형식
     * @param targetFormat : 변환할 문자열 형식
     * @return 변환된 날짜
     */
    public String toDate(String dateStr, String sourceFormat, String targetFormat){ 
    	try{
			SimpleDateFormat  format = new SimpleDateFormat(sourceFormat);
			Date newDate = format.parse(dateStr);

			SimpleDateFormat  newFormat = new SimpleDateFormat(targetFormat); 
	        return newFormat.format(newDate);
    	}catch(Exception e){
    		System.out.println(e);
    		return dateStr;
    	}
    }
    
    /**
     * Map의 Key 배열 얻기
     * @param map : map 객체
     * @return key 목록을 object 배열로 반환
     */
    public Object[] getKeys(Map map){ 
		if(map!=null){ 
			Set hkeySet = map.keySet(); 
			Object[] keys = hkeySet.toArray(); 
			return keys;
		}
		return null;
    }

    /**
     * Map의 Key 배열 얻기
     * @param map : map 객체
     * @return key 목록을 문자열 배열로 반환
     */
    public String[] getKeysforString(Map map){ 
		if(map!=null){ 
			Set hkeySet = map.keySet(); 
			Object[] keys = hkeySet.toArray();
			String[] strs = new String[keys.length];
			int i=0;
			for(Object key : keys){
				strs[i++]=key.toString();
			}
			return strs;
		}
		return null;
    }
    
    /**
     * SQL Injection Code 삭제
     * @param str
     * @return
     */
	public String removeSQLInjectionCode(String str) {
		str = str.replaceAll("\"", "&#034;");
		str = str.replaceAll("\\$", "&#036;");
		str = str.replaceAll("%", "&#037;");
		str = str.replaceAll("'", "&#039;");
		str = str.replaceAll("\\(", "&#040;");
		str = str.replaceAll("\\)", "&#041;");
		str = str.replaceAll("/", "&#047;");
		str = str.replaceAll("<", "&#060;");
		str = str.replaceAll(">", "&#062;");
		
		return str;
	}

	/**
	 * Cross site scripting 코드 삭제
	 * @param str : 원본 문자열
	 * @return xss 코드 삭제된 문자열
	 */
	public boolean checkXssCode(String str) {
		boolean mode = false;
		String[] xssCodes = {"<script","alert","document.",".cookie","location."};
		if(str!=null){
			for(String code : xssCodes){
				if(str.indexOf(code)!=-1){
					return true;
				}
			}
		}
		
		return mode;
	}
	
	/**
	 * xml 식별자 코드를 ascii 코드로 변환
	 * @param mode : 삭제여부 (true=삭제, false=미삭제)
	 * @param str : 문자열
	 * @return
	 */
	public String escapeXml(boolean mode, String str){
		if(mode){
			return looseEscapeXml(str);
		}else{
			return str!=null?str:"";
		}
	}
	
	/**
	 * xml 식별자 코드를 ascii 코드로 변환
	 * @param str : 문자열
	 * @return xml 식별자 코드가 ascii 코드로 변환 문자열
	 */
	public String escapeXml(String str){
		if(str!=null){
			str = str.replaceAll("\"", "&#034;");
			str = str.replaceAll("'", "&#039;");
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("<", "&lt;");
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * xml 식별자 코드를 ascii 코드로 변환-(p, br)태그 제외
	 * @param str : 문자열
	 * @return xml 식별자 코드가 ascii 코드로 변환 문자열-(p, br)태그 제외
	 */
	public String looseEscapeXml(String str){
		if(str!=null){
			str = escapeXml(str);
			String[] htmlTag = {"p","P","/p","/P","br","BR","bR","Br","/br","/BR","/bR","/Br"};
			
			for(int i=0; i<htmlTag.length; i++){
				str = str.replaceAll("&lt;"+htmlTag[i]+"&gt;", "<"+htmlTag[i]+">");
			}
			
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * xml 식별자 코드가 ascii 코드로 변환된 코드를 식별자 코드로 역변환
	 * @param str
	 * @return
	 */
	public String reverseEscapeXml(String str){
		if(str!=null){
			str = str.replaceAll("&#034;", "\"");
			str = str.replaceAll("&#036;", "\\$");
			str = str.replaceAll("&#037;", "%");
			str = str.replaceAll("&#039;", "'");
			str = str.replaceAll("&#040;", "(");
			str = str.replaceAll("&#041;", ")");
			str = str.replaceAll("&#047;", "/");
			str = str.replaceAll("&#060;", ">");
			str = str.replaceAll("&#062;", "<");
			str = str.replaceAll("&gt;", ">");
			str = str.replaceAll("&lt;", "<");
			
			return str;
		}else{
			return "";
		}
	}
	
	/**
	 * 계산
	 * @param flag : 부등호
	 * @param v1 : 값1
	 * @param v2 : 값2
	 * @return v1 과 v2의 부등호 결과 값
	 */
	public boolean checkValue(String flag, double v1, double v2){
		if(flag.equals(">")){
			return (v1>v2);
		}
		if(flag.equals(">=")){
			return (v1>=v2);
		}
		if(flag.equals("=")){
			return (v1==v2);
		}
		if(flag.equals("<=")){
			return (v1<=v2);
		}
		if(flag.equals("<")){
			return (v1<v2);
		}
		
		return false;		
	}
	
	/**
	 * Map 복사
	 * @param map : map 객체
	 * @param keys : 복사할 Key 목록
	 * @return key 목록만 복사된 map객체
	 */
	public Map<String, Object> copyMap(Map<String, Object> map, String[] keys){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if(map!=null&&keys!=null&&keys.length>0){
				for(int i=0; i<keys.length; i++){
					if(map.get(keys[i])!=null){
						result.put(keys[i], map.get(keys[i]));
					}else{
						result.put(keys[i], "");
					}
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return result;
	}
    
	public String changeBigDecimal(double a, int round, boolean mode, String type){
		BigDecimal big = null;
		String value = "";
		try{ 
			String orgValue = ""+a;
			//System.out.println("\n>>>"+a);
			int gisu=0;
			int pointIdx=-1;
			//if(a>MAX_INT){ 
			if(mode){
				if(a==0){
					value="0";
				}else{
					if(orgValue.indexOf("E")>-1){
						gisu = Integer.parseInt(orgValue.substring(orgValue.indexOf("E")+1));
					}else if(orgValue.indexOf("e")>-1){
						gisu = Integer.parseInt(orgValue.substring(orgValue.indexOf("e")+1));
					}
					pointIdx = orgValue.indexOf(".");
					//System.out.println("gisu>>"+gisu);
					//System.out.println("pointIdx>>"+pointIdx);
					
					if(type.toUpperCase().equals("U")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_HALF_UP);
					}else if(type.toUpperCase().equals("D")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_DOWN);
					}else if(type.toUpperCase().equals("C")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_CEILING);
					}
					/*
					else{
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_FLOOR);
						new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_FLOOR);
					}
					*/
					//value = mode?big.toString():big.stripTrailingZeros().toString();
					if(gisu==0){
						if(pointIdx>-1){
							
						}
					}
					value = getDoubleString(big.toString());
				}
			}else{
				value = String.valueOf(a);
				String zero = value.substring(value.indexOf(".")+1); 
				if(Integer.parseInt(zero)==0){
					value = value.substring(0, value.indexOf("."));
				}
			} 
		}catch(Exception e){
			//e.printStackTrace();
			//System.out.println(e.toString());
			value = ""+a;
		}
		return value;
	} 
    
	public String changeBigDecimal2(String dv, int round, boolean mode, String type){
		BigDecimal big = null;
		String value = "";
		double a = 0;
		try{ 
			a = toDouble(dv);
			
			String orgValue = ""+a;
			//System.out.println("\n>>>"+dv+"\t>>>"+orgValue);
			int gisu=0;
			int gisuIdx=-1;
			int pointIdx=-1;
			//if(a>MAX_INT){ 
			if(mode){
				if(a==0){
					value="0";
				}else{
					if(orgValue.indexOf("E")>-1){
						gisu = Integer.parseInt(orgValue.substring(orgValue.indexOf("E")+1));
						gisuIdx = orgValue.indexOf("E");
					}else if(orgValue.indexOf("e")>-1){
						gisu = Integer.parseInt(orgValue.substring(orgValue.indexOf("e")+1));
						gisuIdx = orgValue.indexOf("e");
					}
					pointIdx = orgValue.indexOf(".");
					//System.out.println("gisu>>"+gisu +"\tgisuIdx>>"+gisuIdx+"\tpointIdx>>"+pointIdx);
					//System.out.print("round>>"+round);
					if(gisuIdx<0){
						if(pointIdx<0){
							round = 0;
						}else if(orgValue.length()-pointIdx-1<round){
							round = orgValue.length()-pointIdx-1;
						}
					}else{
						if(pointIdx<0){
							if(Math.abs(gisu)<round){
								round=Math.abs(gisu);
							}
						}else{
							gisu=orgValue.substring(0,pointIdx).replaceAll("-", "").length()+Math.abs(gisu);
							if(gisu<round){
								round=gisu;
							}
						}
					}
					//System.out.println("\treround>>"+round);
					
					if(type.toUpperCase().equals("U")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_HALF_UP);
					}else if(type.toUpperCase().equals("D")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_DOWN);
					}else if(type.toUpperCase().equals("C")){
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_CEILING);
					}
					/*
					else{
						big = new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_FLOOR);
						new BigDecimal(a).setScale(round,java.math.BigDecimal.ROUND_FLOOR);
					}
					*/
					//value = mode?big.toString():big.stripTrailingZeros().toString();
					if(gisu==0){
						if(pointIdx>-1){
							
						}
					}
					value = getDoubleString(big.toString());
				}
			}else{
				value = String.valueOf(a);
				String zero = value.substring(value.indexOf(".")+1); 
				if(Integer.parseInt(zero)==0){
					value = value.substring(0, value.indexOf("."));
				}
			} 
		}catch(Exception e){
			//e.printStackTrace();
			//System.out.println(e.toString());
			value = ""+a;
		}
		return value;
	} 
	
	public String getDoubleString(String value){
		String reValue="";
		//System.out.println("AA "+value);
		value = getDoubleStringForExponente(value);
		if(value.indexOf(".")>=0){
			int last=value.indexOf(".");
			//System.out.println(value.substring(0, last));
			for(int i=value.length()-1; i>last; i--){
				if(!value.substring(i, i+1).equals("0")){
					last=i+1;
					break;
				}
			}
			reValue = value.substring(0, last);
		}else{
			reValue = value;
		}
		//System.out.println("BB "+reValue);
		return reValue;
	}
	
	public String getDoubleStringForExponente(String value){
		String reValue="";
		if(value.indexOf("E")>-1 || value.indexOf("e")>-1){

			int gisu=0;
			int gisuIdx=-1;
			int pointIdx=-1;
			boolean isPlus = true;
			if(value.length()>0 && value.substring(0, 1).equals("-")){
				isPlus = false;
			}
			if(value.indexOf("E")>-1){
				gisu = Integer.parseInt(value.substring(value.indexOf("E")+1));
				gisuIdx = value.indexOf("E");
			}else if(value.indexOf("e")>-1){
				gisu = Integer.parseInt(value.substring(value.indexOf("e")+1));
				gisuIdx = value.indexOf("e");
			}
			pointIdx = value.indexOf(".");
			//System.out.println(isPlus+"  gisu>>"+gisu +"\tgisuIdx>>"+gisuIdx+"\tpointIdx>>"+pointIdx);

			StringBuilder str = new StringBuilder();
			if(gisu<0){
				if(!isPlus){
					str.append("-");
				}
				str.append("0.");
				for(int i=1; i<Math.abs(gisu); i++){
					str.append("0");
				}
				str.append(value.substring(0, gisuIdx).replaceAll("\\.", "").replaceAll("-", ""));
			}else{
				str.append(value);
			}

			reValue = str.toString();
		}else{
			reValue = value;
		}

		return reValue;
	}
	//-12345678910123.0E-10
	
	public String getDoubleString(double value){
		String reValue = "";

		NumberFormat f = NumberFormat.getInstance();
		f.setGroupingUsed(false);
		reValue = f.format(value);
		
		return reValue;
	}


	/**
	 * 바이트 단위로 문자열 자르기
	 * @param str : 자를 문자열
	 * @param byteLength : 바이트수
	 * @return
	 */
	public String cutWordInByteLegnth(String str, int byteLength){
		StringBuffer sbStr = new StringBuffer(byteLength);
		int iTotal=0;
		for(char c: str.toCharArray()){
			iTotal+=String.valueOf(c).getBytes().length;
			if(iTotal>byteLength){
				break;
			}
			sbStr.append(c);
		}
		return sbStr.toString();
	}
}
