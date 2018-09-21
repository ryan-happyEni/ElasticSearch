<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>Elastic Search Tools.</title>
		<style type="text/css">
		.header2{
		color:#cdcdcd;
		}
		.header6{
		color:#cdcdcd;
		}
		.el_control_section{
		width:40%;heigth:100%;min-height:300px;border:1px solid #cdcdcd;overflow:auto;float:left;color:#353535;background:#f8f8f8;
		}
		
		.el_view_section{
		width:59%;heigth:100%;min-height:300px;border:1px solid #cdcdcd;overflow:auto;float:left;margin-left:2px;color:#353535;background:#f8f8f8;
		}
		.clear{
		clear:both;
		}
		.row{clear:both;padding:10px;}
		.col{float:left;}
		.mg10{margin:10px;}
		.pd10{padding:10px;}
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
		.border-r{border-right:1px solid #cdcdcd;}
		.btngroup button{margin-bottom:10px;}
		input{border:1px solid #cdcdcd;padding:3px; color:#353535}
		textarea{border:0px solid #cdcdcd;padding:3px; color:#353535}
		hr{border:0.5px solid #cdcdcd;}
		body{background:#3a3a3a;}
		table{width:100%;border-spacing:0px; border:0px solid #b0b0b0;border-collapse : collapse;}
		th, td{border:1px solid #b0b0b0;padding:3px;font-size:12px;}
		th{background:#d2d2d2;}
		</style>
		
		<script src="/static/js/jquery/jquery.min.1.7.2.js" type="text/javascript" charset="utf-8"></script>
		<script src="/static/js/common_util.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<h2 class="header2">Elastic Search Tool</h2>
		<hr>
		<div class="el_control_section">
			<div class="row">
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
					<div class="row">
						<div class="col w50 tx_right"><span>Search Query (split[/])</span>&nbsp;&nbsp;</div>
						<div class="col w50 tx_left"><input type="text" id="_search" value=""></div>
					</div>
				</div>
				<div class="col w30">
				</div>
			</div>
			<div class="row">
				<hr>
			</div>
			<div class="row">
				<div class="col w50">
					<button id="btn_column_add">Add Column</button>
					<button id="btn_column_del">Del Column</button>
				</div>
				<div class="col w50 tx_right btngroup">
					<button id="btn_search">Search</button>
					<button id="btn_get">Get</button>
					<button id="btn_put">Put</button>
					<button id="btn_update">Update</button>
					<button id="btn_delete">Delete</button>
				</div>
			</div>
			<div class="row">
				<div id="header">
					<div class="row">
						<div class="col w50 tx_right" style="border-top:1px solid #cdcdcd;"><div class="pd10 border-r">Key</div></div>
						<div class="col w50 tx_left" style="border-top:1px solid #cdcdcd;"><div class="pd10">Value</div></div>
					</div>
				</div>
				<div id="rows" style="width:100%;min-height:200px;overflow:auto;border:1px solid #cdcdcd;background:#ffffff;"></div>
			</div>
			<div class="row"></div>
		</div>
		<div class="el_view_section">
			<div class="mg10">
				<textarea id="el_view_data" style="width:100%;height:254px;border:1px solid #cdcdcd;background:#4c4c4c;color:#ffffff;"></textarea>
			</div>
			<div class="row">
				<div id="datas_section" style="width:100%;min-height:200px;overflow:auto;border:1px solid #cdcdcd;background:#ffffff;">
					<table id="datas">
						<thead></thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class="row"></div>
		</div>
		<div class="clear"></div>
		<div><h6 class="header6">@Happy2ni, 2018</h6></div>
		
		
		<script type="text/javascript">
		var contextPath = "";
		
		function processOn(){}
		
		function processOff(){}
		
		function addColumn(){
			var row = document.createElement("div");
			$("#rows").append(row);
			row.className="row";
			row.id="row_"+rowidx;

			var col = document.createElement("div");
			row.appendChild(col);
			col.className="col w50 tx_right";

			var txtdiv = document.createElement("div");
			col.appendChild(txtdiv);
			txtdiv.className="pd10 border-r";

			var chk = document.createElement("input");
			txtdiv.appendChild(chk);
			chk.type="checkbox";
			chk.value="row_"+rowidx;

			var inp = document.createElement("input");
			txtdiv.appendChild(inp);
			inp.type="text";
			inp.id="key_"+rowidx;
			inp.style="width:90%";


			var col = document.createElement("div");
			row.appendChild(col);
			col.className="col w50 tx_left";

			var txtdiv = document.createElement("div");
			col.appendChild(txtdiv);
			txtdiv.className="pd10";

			var inp = document.createElement("input");
			txtdiv.appendChild(inp);
			inp.type="text";
			inp.id="value_"+rowidx;
			inp.style="width:90%";
			
			rowidx++;
		}

		
		function delColumn(){
			var chks = $("input[type=checkbox]:checked");
			if(chks.length>0){
				for(var i=chks.length-1; i>=0; i--){
					$("#"+chks[i].value).remove();
				}
			}
		}
		var rowidx=0;
		
		
		var data_idx=0;
		function addRow(data){
			data_idx=0;
			
			if(data.length==null && data.length == undefined){
				data = [data];
			}

			var head = $("#datas > thead");
			head.empty();
			
			var datas = $("#datas > tbody");
			datas.empty();

			var size = data.length;
			if(size>0){

				var row = document.createElement("tr");
				datas.append(row);
				row.className="row";
				row.id="data_"+data_idx;
				for(var key in data[0]){
					var col = document.createElement("th");
					row.appendChild(col);
					row.className="row";
					col.innerHTML=key;
				}
				
				for(var i=0; i<size; i++){
					var row = document.createElement("tr");
					datas.append(row);
					row.className="row";
					row.id="data_"+data_idx;
					for(var key in data[i]){
						var col = document.createElement("td");
						row.appendChild(col);
						row.className="row";
						col.innerHTML=data[i][key];
					}
				}
			}
			
		}
		
		function initHeight(){
			var top_bottom = 160;
			var height=window.innerHeight-top_bottom;
			
			$("#datas_section").css("height", height-300);
			$("#rows").css("height", height-300);
		}
		initHeight()
		
		$("#btn_search").click(function(){sendSearch($("#index").val(), $("#type").val(), $("#id").val());});
		$("#btn_get").click(function(){sendGet($("#index").val(), $("#type").val(), $("#id").val());});
		$("#btn_put").click(function(){sendPut($("#index").val(), $("#type").val(), $("#id").val());});
		$("#btn_column_add").click(function(){addColumn();});
		$("#btn_column_del").click(function(){delColumn();});
		window.onresize=function(){initHeight();}
		
		</script>
		<script type="text/javascript">
		function sendSearch(index, type, id){
			var search_query = $("#_search").val().split("/");
			if(search_query==""){
				$("#_search").focus();
				return;
			}
	        var param = {};
	        for(var i=0; i<search_query.length; i++){
	        	var query = search_query[i].split(":");
	        	param[query[0]]=query[1];
	        }
	    	param = JSON.stringify(param);
	    	var request_type = "POST";
	    	var content_type = "application/json; charset=UTF-8";
			var return_data_type = 'json';
			var call_url = contextPath+"/elastic/search/"+index+"/"+type;
			
			sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
				addRow(data);
				$("#el_view_data").val(JSON.stringify(data,null,2));
			}, true);
		}
		function sendGet(index, type, id){
	        var param = {};
	    	param = JSON.stringify(param);
	    	var request_type = "POST";
	    	var content_type = "application/json; charset=UTF-8";
			var return_data_type = 'json';
			var call_url = contextPath+"/elastic/put/"+index+"/"+type+"/"+id;
			
			sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
				addRow(data);
				$("#el_view_data").val(JSON.stringify(data,null,2));
			}, true);
		}

		function sendPut(index, type, id){
	        var param = {};

			var chks = $("input[type=checkbox]");
			if(chks.length>0){
				for(var i=0; i<chks.length; i++){
					var idx = chks[i].value.replace("row_", "");
					if($("#key_"+idx).val()==""){
						$("#key_"+idx).focus();
						return;
					}else if($("#value_"+idx).val()==""){
						$("#value_"+idx).focus();
						return;
					}else{
						$("#key_"+idx).val($("#key_"+idx).val().replace(/ /gi, ""));
						param[$("#key_"+idx).val()]= $("#value_"+idx).val();
					}
				}
			}
			
			if(Object.keys(param).length>0){
		    	param = JSON.stringify(param);
		    	var request_type = "POST";
		    	var content_type = "application/json; charset=UTF-8";
				var return_data_type = 'json';
				var call_url = contextPath+"/elastic/put/"+index+"/"+type+"/"+id;
				
				sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
					console.log(data)
					$("#el_view_data").val(JSON.stringify(data,null,2));
				}, true);
			}else{
				
			}
		}
		
		</script>
	</body>
</html>