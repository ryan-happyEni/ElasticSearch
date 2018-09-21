<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

CategoryList

<form method="POST" class="form-signin">
	<input type="text" id="cate_idx" />
	<input type="text" id="cate_name" />
	<input type="text" id="cate_type" />
	<input type="text" id="parent_cate_idx" />
	<input type="text" id="order_no" />
	<input type="text" id="view_yn" />
		
    <button class="btn btn-lg btn-primary btn-block" type="button" id="btn_save">Submit</button>
</form>

<button class="btn btn-lg btn-primary btn-block" type="button" id="btn_search">Search</button>
<table border="1">
	<thead>
		<th>idx</th>
		<th>name</th>
		<th>type</th>
		<th>parent cate idx</th>
		<th>order no</th>
		<th>view yn</th>
	</thead>
	<tbody id="tbl_body">
	</tbody>
</table>
<div id="div_page"></div>

<script src="${ contextPath }/resources/js/jquery/jquery.min.1.7.2.js"></script>
<script type="text/javascript">
var contextPath = "${ contextPath }";
function doSave(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");	
	
	console.log("test1111");
	var cate_idx = $("#cate_idx");
	var cate_name = $("#cate_name");
	var cate_type = $("#cate_type");
	var parent_cate_idx = $("#parent_cate_idx");
	var order_no = $("#order_no");
	var view_yn = $("#view_yn");
	
	
	var params =  {};
	params.cate_idx=cate_idx.val();
	params.cate_name=cate_name.val();
	params.cate_type=cate_type.val();
	params.parent_cate_idx=parent_cate_idx.val();
	params.order_no=order_no.val();
	params.view_yn=view_yn.val();
	
	console.log(params)
	$.ajax({
		url: contextPath+"/admin/common/board/category/add_category",
        type:'POST',
		//contentType: "application/json",
        data: params,
		//dataType: 'json',
		/*
		beforeSend: function( xhr ) {
		    xhr.setRequestHeader(header, token);
		},
		*/
        success:function(data){
        	console.log(data);
        	if(data=="success"){
        		doSearch(1);
        	}else{
        		console.log("fail");
        	}
        },
        error:function(jqXHR, textStatus, errorThrown){
			console.log('error end=' + textStatus + ' : ' + errorThrown);
        }
    });
}


function doSearch(page){
	var list_size=10;
	var page=2;
	
	var params =  {};
	params.current_page=page;
	params.list_size=list_size;
	$.ajax({
		url: contextPath+"/admin/common/board/category/list",
        type:'POST',
        data: params,
        success:function(data){
        	var tbl_info={};
        	tbl_info.body_id="tbl_body";
        	tbl_info.paging_id="div_page";
        	tbl_info.header_cnt=6;
        	tbl_info.cols=["cate_idx","cate_name","cate_type","parent_cate_idx","order_no","view_yn"];
        	
        	makeList(tbl_info, data);
        	console.log(data);
        },
        error:function(jqXHR, textStatus, errorThrown){
			console.log(jqXHR);
			console.log('error end=' + textStatus + ' : ' + errorThrown);
        }
    });
}


function makeList(tbl_info, data){
	var tbody = $("#"+tbl_info.body_id);
	tbody.empty();

	var paging  = $("#"+tbl_info.paging_id);
	paging.empty();
	if(data!=null){
		var list = data;
		var size = list.length;
		var cols = tbl_info.cols;
		var total_page=1;
		var current_page=1;
		var list_size=10;
		for(var i=0;i<size; i++){
			var tr = document.createElement("tr");
			tbody.append(tr);
			for(var c=0;c<tbl_info.header_cnt; c++){
				var td = document.createElement("td");
				td.innerHTML = data[i][cols[c]];
				tr.appendChild(td);
			}
			if(i==0){
				total_page =data[i]["total_page"];
				current_page =data[i]["current_page"];
				list_size =data[i]["list_size"];
			}
		}
		
		var ps = 1;
		var pe = 1;
		var pcnt = 10;
		ps = (current_page/pcnt)+1;
		pe = ps+pcnt-1;
		for(var p=0;p<total_page; p++){
			var a = document.createElement("a");
			a.innerHTML = "<1>";
			a.onclick=function(){doSearch(1);}
			a.style.cursor="pointer";
			paging.append(a);
		}
		
	}else{
		var tr = document.createElement("tr");
		tbody.append(tr);
		var td = document.createElement("td");
		tr.appendChild(td);
		td.innerHTML = "조회된 내용이 없습니다.";
		td.colspan=tbl_info.header_cnt;
		

		var a = document.createElement("a");
		a.innerHTML = "<1>";
		a.onclick=function(){doSearch(1);}
		a.style.cursor="pointer";
		paging.append(a);
	}
}


function initBtn(){
	$("#btn_save").on("click",function (event) {
		doSave();
	});
	
	$("#btn_search").on("click",function (event) {
		doSearch();
	});
	
	doSearch(1);
}

initBtn();
</script>