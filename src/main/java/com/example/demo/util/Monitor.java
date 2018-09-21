package com.example.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Monitor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String errorFilePath;
    private boolean errorFileMake=false;
    private boolean useSendMail=false;
    private String smtpHost;
    private int port;
    private String id;
    private String pw;
    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String toName;

    public Monitor(){
    }
    
    public Monitor(String errorFilePath, boolean useSendMail, String smtpHost, int port, String id, String pw, String fromEmail, String fromName, String toEmail, String toName){
    	this.errorFilePath=errorFilePath;
    	if(errorFilePath!=null&&!errorFilePath.equals("")){
    		this.errorFileMake=true;
    	}
    	
    	this.useSendMail=useSendMail;
        this.smtpHost=smtpHost;
        this.port=port;
        this.id=id;
        this.pw=pw;
        this.fromEmail=fromEmail;
        this.fromName=fromName;
        this.toEmail=toEmail;
        this.toName=toName;
    }
    
    public Monitor(String errorFilePath){
    	this.errorFilePath=errorFilePath;
    	if(errorFilePath!=null&&!errorFilePath.equals("")){
    		this.errorFileMake=true;
    	}
    }
    
    public void info(String message){
		logger.info(message);
    }
    
    public void debug(String message){
		logger.debug(message);
    }

    public void error(String message){
		printError(message);
    }

    public void error(Exception e){
    	error(e, "error.", null);
    }

    public void error(String message, Exception e){
    	error(e, message, null);
    }

    public void error(String message, Exception e, Map map){
    	error(e, message, map);
    }

    public void error(Exception e, String message){
    	error(e, message, null);
    }

    public void error(Exception e, String message, Map map){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pinrtStream = new PrintStream(out);
		
		Throwable ta = e.getCause();
		if(ta!=null){
			ta.printStackTrace(pinrtStream);
		}else{
			e.printStackTrace(pinrtStream);
		}

		StringBuilder str = new StringBuilder();
		str.append("\n### Error report start ### ");
		str.append("\n# title : ").append(message!=null&&message.length()>50?message.substring(0, 50):message);
		str.append("\n# time : ").append(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:dd"));
		
		if(map!=null){
			str.append("\n# map : ").append(map.toString());
		}
		
		str.append("\n# couse : ").append(message).append("\n").append(e.toString()).append("\n");
		str.append(out.toString());
		str.append("\n### Error report end   ### ");
		logger.error(str.toString());
		//System.out.println(str.toString());
		
		sendMail(message, str.toString());
    }

    public void printError(String message){  
		StringBuilder str = new StringBuilder();
		str.append("\n### Error report start ### ");
		str.append("\n# title : ").append(message!=null&&message.length()>50?message.substring(0, 50):message);
		str.append("\n# time : ").append(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:dd"));
		str.append("\n# couse : ").append("\n");
		str.append(message);
		str.append("\n### Error report end   ### ");
		logger.error(str.toString());
		//System.out.println(str.toString());
		sendMail(message, str.toString());
    }
    
    public void sendMail(String title, String message){
    	try{
    		logger.error("## Send Mail ###");
    		/*
    		if(this.errorFileMake&&this.errorFilePath!=null){
        		FileUtil futil = new FileUtil();
        		CommonUtil cutil = new CommonUtil();
	    		message = message.replaceAll("\n", "\n\r");
        		logger.error("## Error file path ###"+this.errorFilePath);
    			String erroFiileName = this.errorFilePath+"/error_"+DateUtil.getCurrentTime("yyyy-MM-dd_HHmmss")+".log";
    			futil.checkDirectory(this.errorFilePath);
    			futil.writeFiile(erroFiileName, message);
    			
    			if(useSendMail&&!cutil.toString(smtpHost).equals("")&&!cutil.toString(toEmail).equals("")&&!cutil.toString(fromEmail).equals("")){
    				try{
	    	    		SMTPSendMail mail = new SMTPSendMail();
	    	    		List<String> fileList = new ArrayList<String>();
	    	    		fileList.add(erroFiileName);
	    	    		if(port>0&&!cutil.toString(id).equals("")&&!cutil.toString(pw).equals("")){
	    					mail.sendMail(smtpHost, port, id, pw, fromEmail, fromName, toEmail, title, message, fileList);
	    	    		}else{
	        	    		mail.sendMail(smtpHost, fromEmail, fromName, toEmail, title, message, fileList);
	    	    		}
    		    	}catch(Exception e){
    		    		System.out.println(e.toString());
    		    		e.printStackTrace();
    		    	}
    			}
    		}
    		*/
    		System.out.println("## SendMail End###");
    	}catch(Exception e){
    		System.out.println(e.toString());
    		e.printStackTrace();
    	}
    }
}
