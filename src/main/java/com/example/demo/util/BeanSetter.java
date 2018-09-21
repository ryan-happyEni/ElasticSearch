package com.example.demo.util;

import java.lang.reflect.Method;
import java.util.Map;

public class BeanSetter {
    public static Object setBean(Map<String, Object> map, Class cls){
        Object obj=null;
        try{ 
             obj = cls.newInstance();                     
             Method[] methods = cls.getDeclaredMethods();  
             Class[] paramTypes;
             String methodName = "";
             String key=""; 
             Object value;
                  
             for(Method m : methods){
                 methodName = m.getName();
                 if(methodName.length()>3&&methodName.startsWith("set")){
                     //System.out.println();
                     key = methodName.substring(3, 4).toLowerCase();
                     key += methodName.substring(4, methodName.length());
                     //System.out.println("key = " + key);
                     paramTypes = m.getParameterTypes();
                     if(paramTypes!=null&&paramTypes.length==1){
                         try{
                             for(int i=0; i<1; i++){
                                //System.out.print(" params = " +paramTypes[i]); 
                                value = map.get(key);
                                
                                if(paramTypes[i].equals(int.class)&&value!=null){ 
                                    m.invoke(obj, Integer.parseInt(value.toString()));
                                }else if(paramTypes[i].equals(Integer.class)&&value!=null){
                                    m.invoke(obj, Integer.parseInt(value.toString()));
                                }else if(paramTypes[i].equals(long.class)&&value!=null){
                                    m.invoke(obj, Long.parseLong(value.toString()));
                                }else if(paramTypes[i].equals(Long.class)&&value!=null){
                                    m.invoke(obj, Long.parseLong(value.toString()));
                                }else if(paramTypes[i].equals(float.class)&&value!=null){
                                    m.invoke(obj, Float.parseFloat(value.toString()));
                                }else if(paramTypes[i].equals(Float.class)&&value!=null){
                                    m.invoke(obj, Float.parseFloat(value.toString()));
                                }else if(paramTypes[i].equals(double.class)&&value!=null){
                                    m.invoke(obj, Double.parseDouble(value.toString()));
                                }else if(paramTypes[i].equals(Double.class)&&value!=null){
                                    m.invoke(obj, Double.parseDouble(value.toString()));
                                }else if(paramTypes[i].equals(char.class)&&value!=null){
                                    m.invoke(obj, new String(value.toString()));
                                }else if(paramTypes[i].equals(boolean.class)&&value!=null){
                                    m.invoke(obj, Boolean.parseBoolean(value.toString()));
                                }else{
                                    //m.invoke(obj, paramTypes[i].cast(map.get(key)));
                                    m.invoke(obj, value);
                                }
                             }
                         }catch(Exception e){
                             System.out.println("invoke error " +e);
                         }
                     }
                 }
             }
        }catch(Exception e){
            System.out.println("setBean error " +e);
        }
        return obj;
    }   
}
