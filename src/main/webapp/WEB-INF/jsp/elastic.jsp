<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>Elastic Search Tools.</title>
		<style type="text/css">
		.header2{
		color:#5D5D5D;
		}
		.header6{
		color:#5D5D5D;
		}
		.el_control_section{
		width:40%;heigth:100%;min-height:300px;border:1px solid #cdcdcd;overflow:auto;float:left;color:#353535;
		}
		
		.el_view_section{
		width:59%;heigth:100%;min-height:300px;border:1px solid #cdcdcd;overflow:auto;float:left;margin-left:2px;color:#353535;
		}
		.clear{
		clear:both;
		}
		.row{clear:both;padding:10px;}
		.col{float:left;}
		.mg10{margin:10px;}
		.w100{width:100%;}
		.w90{width:90%;}
		.w80{width:80%;}
		.w70{width:70%;}
		.w60{width:60%;}
		.w50{width:50%;}
		.w40{width:40%;}
		.w30{width:30%;}
		.w20{width:20%;}
		.w10{width:10%;}
		.tx_left{text-align:left;}
		.tx_center{text-align:center;}
		.tx_right{text-align:right;}
		.btngroup button{margin-bottom:10px;}
		input{border:1px solid #cdcdcd;padding:3px; color:#353535}
		textarea{border:0px solid #cdcdcd;padding:3px; color:#353535}
		</style>
		
		<script src="/static/js/jquery/jquery.min.1.7.2.js" type="text/javascript" charset="utf-8"></script>
		<script src="/static/js/common_util.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<h2 class="header2">Elastic Search Tool</h2>
		<hr>
		<div class="el_control_section">
			<div class="col w70">
				<div class="row">
					<div class="col w50 tx_right"><span>Index</span>&nbsp;&nbsp;</div>
					<div class="col w50 tx_left"><input type="text" id="index" value="bigcode_index"></div>
				</div>
				<div class="row">
					<div class="col w50 tx_right"><span>Type</span>&nbsp;&nbsp;</div>
					<div class="col w50 tx_left"><input type="text" id="type" value="tweet"></div>
				</div>
				<div class="row">
					<div class="col w50 tx_right"><span>Id</span>&nbsp;&nbsp;</div>
					<div class="col w50 tx_left"><input type="text" id="id" value="1"></div>
				</div>
			</div>
			<div class="col w30">
				<div class="row">
					<div class="col w100 tx_right btngroup">
						<button id="btn_get">Get</button><br>
						<button id="btn_put">Put</button><br>
						<button id="btn_update">Update</button><br>
						<button id="btn_delete">Delete</button>
					</div>
				</div>
			</div>
		</div>
		<div class="el_view_section">
			<div class="mg10">
				<textarea id="el_view_data" style="width:100%;height:80%"></textarea>
			</div>
		</div>
		<div class="clear"></div>
		<div><h6 class="header6">@Happy2ni, 2018</h6></div>
		
		<script type="text/javascript">
		var contextPath = "";
		
		function processOn(){}
		
		function processOff(){}
		

		function sendGet(index, type, id){
	        var param = {};
	    	param = JSON.stringify(param);
	    	var request_type = "POST";
	    	var content_type = "application/json; charset=UTF-8";
			var return_data_type = 'json';
			var call_url = contextPath+"/elastic/get/"+index+"/"+type+"/"+id;
			
			sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
				console.log(data)
				$("#el_view_data").val(JSON.stringify(data,null,2));
			}, true);
		}
		
		$("#btn_get").click(function(){sendGet($("#index").val(), $("#type").val(), $("#id").val());});
		
		</script>
	</body>
</html>