


String.prototype.replaceAll = function(searchStr, replaceStr){
    var temp = this;
    while( temp.indexOf( searchStr ) != -1 ){
      temp = temp.replace( searchStr, replaceStr );
    }
    return temp;
} 


String.prototype.reverseXSS = function(){
    var temp = this;

    temp = temp.replaceAll("&#034;", "\"");
    temp = temp.replaceAll("&#036;", "\\$");
    temp = temp.replaceAll("&#037;", "%");
    temp = temp.replaceAll("&#039;", "'");
    temp = temp.replaceAll("&#040;", "(");
    temp = temp.replaceAll("&#041;", ")");
    temp = temp.replaceAll("&#047;", "/");
    temp = temp.replaceAll("&#060;", "<");
    temp = temp.replaceAll("&#062;", ">");
    temp = temp.replaceAll("&gt;", ">");
    temp = temp.replaceAll("&lt;", "<");
    
    return temp;
} 

function numberWithCommas(x) {
	if(x!=null){
		x = ""+x;
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}else{
		return "";
	}
}

function toNumberFormat(str, number_type){
	if(str!=null && str!=""){
		str = str+"";
	    var tempStr = str;
	    var exStr = new Array(",",".");
	    
	    var pointStr="";
	    if(str.indexOf(".")>-1){
	    	tempStr = str.substring(0, str.indexOf("."));
	    	pointStr = str.substring(str.indexOf("."));
	    }
	    
	    if(number_type!=null&&number_type==-1){
	    	if(str.indexOf("-")==-1) return "";
	    }else if(number_type!=null&&number_type==1){
	    	if(str.indexOf("-")!=-1) return "";
	    }
	    
	    for(var i=0; i<exStr.length; i++){
	        tempStr = tempStr.replaceAll(exStr[i], "");
	    }
	    
	    if(isNaN(tempStr) == true) { 
	        return tempStr;
	    }else{
	    	var monStr = "";   
	    	if(tempStr.indexOf("-")!=-1){ 
	    		monStr = "-";
	    		tempStr = tempStr.replace("-","");
	    	}
	
	    	var len = tempStr.length;
	    	for(var i=0; i<len; i++){ 
	    		monStr += tempStr.substring(i, i+1); 
	    		if((len>3&&i+1!=len)&&(len-(i+1))%3==0){
	    			monStr +=",";
	    		}
	    	}
	    	
	    	monStr += pointStr;
	    	return monStr;
	    }
	}
	return "";
}


function getCurrentDate(format){
	var date1 = new Date();
	
	return date1.getDateString(format);
}


Number.prototype.to2 = function() { return (this > 9 ? "" : "0")+this; };
Date.prototype.getDateString = function(dateFormat) {
	var result = "";
	
	dateFormat = dateFormat == 8 && "YYYY.MM.DD" || dateFormat == 6 && "hh:mm:ss" || dateFormat || "YYYY.MM.DD hh:mm:ss";
	for (var i = 0; i < dateFormat.length; i++) {
		result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
		dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
		dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
		dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
		dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
		dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
		dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
		dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
		dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
		dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
		dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
		dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
		(dateFormat.charAt(i)                         ) ;
	}
	return result;
}; 


function dateAdd(date, interval, units) {
	var ret = new Date(date); //don't change original date
	var checkRollover = function() { if(ret.getDate() != date.getDate()) ret.setDate(0);};
	switch(interval.toLowerCase()) {
		case 'year'   :  ret.setFullYear(ret.getFullYear() + units); checkRollover();  break;
		case 'quarter':  ret.setMonth(ret.getMonth() + 3*units); checkRollover();  break;
		case 'month'  :  ret.setMonth(ret.getMonth() + units); checkRollover();  break;
		case 'week'   :  ret.setDate(ret.getDate() + 7*units);  break;
		case 'day'    :  ret.setDate(ret.getDate() + units);  break;
		case 'hour'   :  ret.setTime(ret.getTime() + units*3600000);  break;
		case 'minute' :  ret.setTime(ret.getTime() + units*60000);  break;
		case 'second' :  ret.setTime(ret.getTime() + units*1000);  break;
		default       :  ret = undefined;  break;
	}
	return ret;
}


function toDate(dateStr, dateFormat){
	var st_y = dateStr.substring(0, 4);
	var st_m = dateStr.substring(5, 7);
	var st_d = dateStr.substring(8, 10);
	var st_hh = dateStr.substring(11, 13);
	var st_mi = dateStr.substring(14, 16);
	var st_ss = dateStr.substring(17, 19);
	

	var dt = new Date(st_y, Number(st_m)-1, st_d, st_hh, st_mi, st_ss);
	
	return dt.getDateString(dateFormat);
}


function getLocalDate(yyy, mmm, ddd, hhh, mmmm, formatStr){
	var date1 = new Date(Number(yyy), Number(mmm)-1,Number(ddd),Number(hhh),Number(mmmm)); 
	
	var format = "YYYY.MM.DD hh:mm";
	if(formatStr!=null && formatStr!=""){
		format = formatStr;
	}
	
	var labourDay = new Date();
	var labourDayOffset = (labourDay.getTimezoneOffset() / 60);
	
	
	var redate = dateAdd(date1, 'hour', (labourDayOffset)*-1);
	
	return redate.getDateString(format);
}


Map = function(){
    this.map = new Object();
};   
Map.prototype = {
    put : function(key, value){
        this.map[key] = value;
    },
    
    get : function(key){
        return this.map[key];
    },

    containsKey : function(key){
        return key in this.map;
    },

    containsValue : function(value){
        for(var prop in this.map){
            if(this.map[prop] == value) return true;
        }
        return false;
    },

    isEmpty : function(key){    
        return (this.size() == 0);
    },

    clear : function(){   
        for(var prop in this.map){
            delete this.map[prop];
        }
    },
    
    remove : function(key){
        delete this.map[key];
    },

    keys : function(){
        var keys = new Array();
        for(var prop in this.map){
            keys.push(prop);
        }
        return keys;
    },

    values : function(){
        var values = new Array();
        for(var prop in this.map){
            values.push(this.map[prop]);
        }
        return values;
    },
    
    size : function(){
        var count = 0;
        for (var prop in this.map) {
            count++;
        }
        return count;
    }
};



function isDecimal(obj, type, msg, focusMod){   
	if(!isNaN(obj.value)){ 
	    temp_value = obj.value.toString(); 
	    regexp = /[^(-?)\.0-9]/g; 
	    repexp = ''; 
	    temp_value = temp_value.replace(regexp,repexp); 
	    regexp = ''; 
	    switch(type){ 
	        case 'int':regexp = /[^0-9]/g; break; 
	        case 'double':regexp = /^(-?)([0-9]*)(\.?)([^0-9]*)([0-9]*)([^0-9]*)/; break; 
	        case '-int':regexp = /^(-?)([0-9]*)([^0-9]*)([0-9]*)([^0-9]*)/;break; 
	        case '-double':regexp = /^(-?)([0-9]*)(\.?)([^0-9]*)([0-9]*)([^0-9]*)/; break; 
	        default : regexp = /[^0-9]/g; break; 
	    } 
	    switch(type){ 
	        case 'int':repexp = '';break; 
	        case 'double':repexp = '$1$2$3$5';break; 
	        case '-int':repexp = '$1$2$4';break; 
	        case '-double':repexp = '$1$2$3$5'; break; 
	        default : regexp = /[^0-9]/g; break; 
	    } 
	    temp_value = temp_value.replace(regexp,repexp);  
	    obj.value = temp_value; 
    }else{ 
    	if(msg!=null) alert(msg);
    	if(focusMod==null||focusMod) obj.focus();
    	obj.value = "";
    }
} 


function addValueEvent(id){
	var data_type = $("#"+id).attr("data-type");
	if(data_type!=""){
		if(data_type=="date"){
			$("#"+id).keyup(function(event){
				if(event.keyCode>=37&&event.keyCode<=40){
					
				}else{
					var format = $("#"+this.id).attr("format");
					if(format!=null&&format!=""){
						var max=format.length;
						if(this.value.length>max){
							this.value = this.value.substring(0, max);
						}
						var len = this.value.length;
						var v = this.value;
						var vx="";
						if(format.indexOf(".")>-1||format.indexOf("-")>-1||format.indexOf("/")>-1){
							for(var i=0; i<len; i++){
								var x = v.substring(i, i+1);
								if(x.replace(/[0-9]/g, "").length==0){
									vx+=x;
								}
							}
							this.value = vx;
							v = this.value;
							for(var i=0; i<format.length; i++){ 
								if(v.length<i){
									break;
								}
								if(format.substring(i, i+1).indexOf(".")>-1||format.substring(i, i+1).indexOf("-")>-1||format.substring(i, i+1).indexOf("/")>-1){
									v = v.substring(0, i)+format.substring(i, i+1)+v.substring(i);
								}
							}
							if(v.substring(v.length-1)=="."||v.substring(v.length-1)=="-"||v.substring(v.length-1)=="/"){
								v = v.substring(0, v.length-1);
							}
							
							this.value=v;
						}else{
							for(var i=0; i<len; i++){
								var x = v.substring(i, i+1);
								if(x.replace(/[0-9]/g, "").length==0){
									vx+=x;
								}
								if(vx.length>max){
									break;
								}
							}
							this.value = vx;
						}
					}
				}
			})
		}else if(data_type=="phone"){
			$("#"+id).blur(function(){
				var regExp =  /^\d{2,3}-\d{3,4}-\d{4}$/;
				var vl = this.value;
				if( !regExp.test(vl) ) {
					this.value = "";
				}
			});
		}else if(data_type=="email"){
			$("#"+id).blur(function(){
				var regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
				   
				var vl = this.value;
				if( !regExp.test(vl) ) {
					this.value = "";
				}
			});
		}else if(data_type=="int"){
			$("#"+id).blur(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				if( !regExp.test(vl) ) {
					if(vl.length>0){
						if(vl.substring(0, 1)=='-'){
							vl = vl.substring(1);
							if( !regExp.test(vl) ) {
								this.value = "";
							}
						}else{
							this.value = "";
						}
					}
				}
			});
			$("#"+id).keyup(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				var flag = vl.length>0? vl.substring(0, 1):"";

				if(vl.length==1 && flag=='-'){
					
				}else{
					if( !regExp.test(vl) ) {
						if(vl.length>0){
							if(vl.substring(0, 1)=='-'){
								vl = vl.substring(1);
								if( !regExp.test(vl) ) {
									this.value = "";
								}
							}else{
								if(vl.length>1){
									this.value = "";
								}
							}
						}
					}
				}
			});
		}else if(data_type=="int_plus"){
			$("#"+id).blur(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				if( !regExp.test(vl) ) {
					this.value = "";
				}
			});
			$("#"+id).keyup(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				if( !regExp.test(vl) ) {
					this.value = "";
				}
			});
		}else if(data_type=="int_minus"){
			$("#"+id).blur(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				if(vl.length>0){
					if(vl.substring(0, 1)=='-'){
						vl = vl.substring(1);
						if( !regExp.test(vl) ) {
							this.value = "";
						}
					}else{
						this.value = "";
					}
				}
			});
			$("#"+id).keyup(function(){
				var regExp = /^\d+$/;
				   
				var vl = this.value;
				if(vl.length>0){
					if(vl.substring(0, 1)=='-'){
						vl = vl.substring(1);
						if( !regExp.test(vl) ) {
							this.value = "";
						}
					}else{
						if(vl.length>1){
							this.value = "";
						}
					}
				}
			});
		}else{
			$("#"+id).blur(function(){
				var dtype = $("#"+this.id).attr("data-type");
				this.value=this.value.replaceAll(",","");
				isDecimal(this, dtype, null, true);
				var v = this.value;
				var point = $("#"+this.id).attr("point-length");
				var format = $("#"+this.id).attr("format");

				var isToForamt = false;
				if(format!=null&&format!=""){
					isToForamt = true;
					if(format.indexOf(".")>-1){
						var tt = format.substring(format.indexOf(".")+1);
						point = ""+tt.length;
					}else{
						point="0";
					}
				}
				
				if(point!=null&&point!=""&&v.indexOf(".")>0){
					if(!isNaN(point)){ 
						point = Number(point);
						var p = v.substring(0, v.indexOf("."));
						var n = v.substring(v.indexOf(".")+1);
						var nn="";
						for(var i=0; i<point; i++){
							nn+=n.substring(i, i+1);
						}
						if(nn!=""){
							v = p+"."+nn;
						}else{
							v = p;
						}
						this.value = v;
					}
				}
				if(isToForamt){
					try{
						var tt = Number(this.value);
						this.value = toNumberFormat(this.value);
					}catch(e){
						this.value = "";
					}
				}
			})
			$("#"+id).keyup(function(event){
				if(event.keyCode>=37&&event.keyCode<=40){
					
				}else{
					var dtype = $("#"+this.id).attr("data-type");
					this.value=this.value.replaceAll(",","");
					isDecimal(this, dtype, null, true);
					var format = $("#"+this.id).attr("format");
					if(format!=null&&format!=""){
						try{
							var tt = Number(this.value);
							this.value = toNumberFormat(this.value);
						}catch(e){
							this.value = "";
						}
					}
				}
			})
		}
	}
}


function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}


function sendRequest(request_type, call_url, param, content_type, return_data_type, call_back_fnc, display_processing_yn, errFnc){
	if(display_processing_yn!=null && display_processing_yn){
		processOn();
	}
    
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	if(content_type!=null){
		if(return_data_type!=null){
			$.ajax({
				type: request_type,
		        contentType: content_type,
				url: call_url,
				data: param,
				dataType: return_data_type,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}else{
			$.ajax({
				type: request_type,
		        contentType: content_type,
				processData: false,
				contentType: false,
				url: call_url,
				data: param,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}
	}else{
		if(return_data_type!=null){
			$.ajax({
				type: request_type,
				url: call_url,
				data: param,
				dataType: return_data_type,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}else{
			$.ajax({
				type: request_type,
				processData: false,
				contentType: false,
				url: call_url,
				data: param,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					if(display_processing_yn!=null && display_processing_yn){
						processOff();
					}
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}
	}
}

function sendRequestErrorHandler(e, errFnc){
	var errorData = JSON.parse(JSON.stringify(e));
	if(errFnc!=null){
		errFnc(errorData);
	}
}


function buildTable(table_id, params, data, view_id, viewFnc, paging_yn, pageFnc, styleFnc){
	var list_header_ths = $("#"+table_id+' > thead > tr > th');
	var tbody = $("#"+table_id+' > tbody');
	tbody.empty();
	if(data!=null){
		if(data.length>0){
			$.each(data, function( index, value ) {
				if(data[0].total_cnt!=null){
					value.total_cnt = data[0].total_cnt;
					value.view_size = data[0].view_size;
				}
				tbody.append(addTableData(list_header_ths, value, index, params.page, view_id, viewFnc, styleFnc));
			});
		}else{
			var tr = document.createElement("tr");
			tbody.append(tr);
			var td = document.createElement("td");
			tr.appendChild(td);
			td.colSpan=list_header_ths.length;
			td.innerHTML = "empty.";
		}
		if(paging_yn!=null && paging_yn && pageFnc!=null && pageFnc){
			addTablePaging(table_id, data, pageFnc);
		}
	}
}


function addTableData(list_header_ths, data, index, page, view_id, viewFnc, styleFnc){
	var tr = document.createElement("tr");
	if(viewFnc!=null){
		tr.style.cursor="pointer";
		tr.setAttribute("data-toggle", "modal");
		tr.setAttribute("data-target", "#"+view_id);
		tr.onclick=function(){
			viewFnc(data, index, page);
		}
	}
	
	var size = list_header_ths.length;
	for(var i=0; i<size; i++){
		var th = $(list_header_ths[i]);
		var code = th.attr("code");
		var data_type = th.attr("data-type");
		var align = th.attr("data-align");
		var format = th.attr("format");
		var point = th.attr("point-length");
		var maxlength = th.attr("maxlength");
		var prefix = th.attr("prefix");
		var suffix = th.attr("suffix");
		var checkboxName = th.attr("checkbox-name");
		var selectName = th.attr("select-name");
		
		if(prefix==null){
			prefix="";
		}
		if(suffix==null){
			suffix="";
		}
		
		if(format!=null && format!=""){
			if(format.indexOf(".")>-1){
				var tt = format.substring(format.indexOf(".")+1);
				point = tt.length;
			}
		}
		
		var txt = data[code]+""; 
		if(txt!=null && txt!=""){
			if(data_type!=null && data_type!=""){
				if(format!=null && data_type=="date"){
					if(txt!=null && txt!="" && txt!="null"){
						txt = toDate(txt, format);
					}else{
						txt="";
					}
				}else if(data_type=='int' || data_type=='float' || data_type=='double'){
					var dtype = data_type;
					txt=""+txt;
					txt=txt.replaceAll(",","");
					try{
						var nn = Number(txt);
						var v = txt;
						if(point!=null&&point!=""&&v.indexOf(".")>0){
							if(!isNaN(point)){ 
								point = Number(point);
								var p = v.substring(0, v.indexOf("."));
								var n = v.substring(v.indexOf(".")+1);
								var nn="";
								var last = point>n.length?n.length:point;
								for(var x=0; x<last; x++){
									nn+=n.substring(x, x+1);
								}
								if(nn!=""){
									v = p+"."+nn;
								}else{
									v = p;
								}
								txt = v;
							}
						}
						if(format!=null&&format!=""){
							txt = toNumberFormat(txt);
						}
					}catch(e){
					}
				}else if(data_type=="checkbox"){
					txt = "<input type='checkbox' id='"+(code+"_"+index)+"' name='"+(checkboxName!=null?checkboxName:code)+"' value='"+data[code]+"'>";
				}else if(data_type=="select"){
					txt = "<select class='form-control' id='"+(code+"_"+index)+"' name='"+(selectName!=null?selectName:code)+"' value='"+data[code]+"'></select>";
				}else if(data_type=="rownum"){
					txt = (data.total_cnt)-((page-1)*data.view_size)-index;
				}else{
				}
			}
			
			if(maxlength!=null && maxlength!="" && !isNaN(maxlength)){
				var max = Number(maxlength);
				if(txt.length>maxlength){
					txt = txt.substring(0, maxlength)+" ...";
				}
			}
		}else{
			txt="";
		}
		
		var td = document.createElement("td");
		tr.appendChild(td);
		if(align!=null && align!=""){
			td.style.textAlign = align;
		}
		td.innerHTML = prefix+txt+suffix;
	}
	
	if(styleFnc!=null){
		styleFnc(tr, data, index);
	}
	
	return tr;
}

var core_ui_paging_idx=0;
function addTablePaging(table_id, data, pageFnc){
	var attrTable={
			view_cnt:10,
			current_page:1,
			last_page:1,
			total_cnt:0
	}
	if(data!=null && data.length>0){
		attrTable.total_cnt=data[0].total_cnt;
		attrTable.current_page=data[0].page;
		attrTable.view_cnt=data[0].view_size;
	}
	var ul = null;
	var totalTxt = null; 
	var call_page = null; 
	var call_page_id = null; 
	var parent = $("#"+table_id)[0].parentNode;
	var paging = $(".paging", parent);
	if(paging!=null && paging.length>0){
		//parent.removeChild(paging[0]);
		ul = $(".paging > div > nav > ul", parent);
		totalTxt = $(".paging > div > div > span", parent);
		totalTxt[0].innerHTML=numberWithCommas(attrTable.total_cnt);
		call_page = $(".paging > div > input", parent);
		call_page_id = call_page[0].id;
	}else{
		core_ui_paging_idx++;
		var row = document.createElement("div");
		parent.appendChild(row);
		row.className="row paging";
		
		var pageDiv = document.createElement("div");
		row.appendChild(pageDiv);
		pageDiv.className="col-sm-10";

		var emptyDiv = document.createElement("div");
		row.appendChild(emptyDiv);
		emptyDiv.className="col-sm-2 text-right";

		call_page_id = "call_page_"+core_ui_paging_idx;
		call_page = document.createElement("input");
		emptyDiv.appendChild(call_page);
		call_page.type="hidden";
		call_page.className="call_page";
		call_page.id=call_page_id;
		call_page.value=attrTable.current_page;
		

		var nav = document.createElement("nav");
		pageDiv.appendChild(nav);
		nav.style.float="left";
		

		ul = document.createElement("ul");
		nav.appendChild(ul);
		ul.className="pagination";


		var li = document.createElement("li");
		ul.appendChild(li);
		li.className="page-item";

		var alink = document.createElement("a");
		li.appendChild(alink);
		alink.className="page-link";
		alink.href="#";
		alink.innerHTML="Prev";


		var li = document.createElement("li");
		ul.appendChild(li);
		li.className="page-item active";

		var alink = document.createElement("a");
		li.appendChild(alink);
		alink.className="page-link";
		alink.href="#";
		alink.innerHTML="1";


		var li = document.createElement("li");
		ul.appendChild(li);
		li.className="page-item";

		var alink = document.createElement("a");
		li.appendChild(alink);
		alink.className="page-link";
		alink.href="#";
		alink.innerHTML="Next";

		var txdiv = document.createElement("div");
		pageDiv.appendChild(txdiv);
		txdiv.style.float="left";
		txdiv.style.marginLeft="20px";
		txdiv.style.padding="0.5rem";
		txdiv.style.lineHeight="1.25";
		txdiv.style.fontSize="0.875rem";
		txdiv.style.border="1px solid #ddd";
		txdiv.style.borderRight="0px";
		txdiv.innerHTML="Size";

		var select = document.createElement("select");
		pageDiv.appendChild(select);
		select.style.float="left";
		select.style.marginLeft="0px";
		select.style.padding="0.36rem 0.5rem";
		select.style.lineHeight="1.25";
		select.style.fontSize="0.875rem";
		select.style.border="1px solid #ddd";
		select.id="sel_call_page_"+core_ui_paging_idx;
		select.addEventListener("change", function(){
			var page = $("#"+this.id.substring(4)).val();
			pageFnc(page);
		});
		
		var opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="10";
		opt.text="10";

		opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="20";
		opt.text="20";

		opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="50";
		opt.text="50";

		opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="100";
		opt.text="100";

		opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="200";
		opt.text="200";

		opt = document.createElement("option");
		select.appendChild(opt);
		opt.value="300";
		opt.text="300";


		var totalTxt = document.createElement("div");
		pageDiv.appendChild(totalTxt);
		totalTxt.style.float="left";
		totalTxt.style.marginLeft="10px";
		totalTxt.style.padding="0.5rem";
		totalTxt.style.lineHeight="1.25";
		totalTxt.style.fontSize="0.875rem";
		totalTxt.style.border="1px solid #ddd";
		totalTxt.innerHTML="Total : <span>"+numberWithCommas(attrTable.total_cnt)+"</span>";
		
		ul = $(ul);
	}

	buildPageNav(ul, attrTable, pageFnc, call_page_id);
}


function pagingViewSize(table_id){
	var parent = $("#"+table_id)[0].parentNode;
	var paging = $(".paging", parent);
	if(paging!=null && paging.length>0){
		select = $(".paging > div > select", parent);
		return select.val();
	}else{
		return 10;
	}
}


function pagingViewSize(table_id){
	var parent = $("#"+table_id)[0].parentNode;
	var paging = $(".paging", parent);
	if(paging!=null && paging.length>0){
		select = $(".paging > div > select", parent);
		return select.val();
	}else{
		return 10;
	}
}


function pagingCallPage(table_id){
	var parent = $("#"+table_id)[0].parentNode;
	var paging = $(".paging", parent);
	if(paging!=null && paging.length>0){
		select = $(".paging > div > .call_page", parent);
		return select.val();
	}else{
		return 10;
	}
}


function buildPageNav(pageNav, pageInfo, callMethodTxt, call_page_id){
	if(pageInfo==null){
		pageInfo ={
			view_cnt:10,
			current_page:1,
			last_page:1,
			total_cnt:0
		}
	}
	if(pageInfo.current_page<1){
		pageInfo.last_page=1;
	}
	if(pageInfo.last_page==0){
		pageInfo.last_page=1;
	}
	pageNav.empty();
	
	var fncName = callMethodTxt.name;
	if(fncName==null || fncName==undefined){
		fncName = callMethodTxt.toString();
		fncName = fncName.substr('function '.length);
		fncName = fncName.substr(0, fncName.indexOf('('));
	}

	var s=1;
	var e=1;
	var page_size=10;
	var last_page = Math.round(pageInfo.total_cnt/pageInfo.view_cnt);
	if(pageInfo.total_cnt>last_page*pageInfo.view_cnt){
		last_page++;
	}
	pageInfo.last_page=last_page;
	
	s = Math.floor( (pageInfo.current_page-1)/page_size, 0)*page_size+1
	e = s+(page_size-1);

	if(e>pageInfo.last_page){
		e=pageInfo.last_page;
	}

	var li = document.createElement("li");
	li.className="page-item";
	li.style.cursor="pointer";
	var a = document.createElement("a");
	a.className="page-link";
	a.innerHTML = "Prev";
	if(s>10){
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(s-1)+";"+fncName+"("+(s-1)+");";
	}
	li.appendChild(a);
	pageNav.append(li);
	
	if(e==0){
		e=1;
	}
	for(var p=s; p<=e; p++){
		var li = document.createElement("li");
		li.className="page-item";
		li.style.cursor="pointer";
		if(p==pageInfo.current_page){
			li.className="page-item active";
		}
		var a = document.createElement("a");
		a.className="page-link";
		a.innerHTML=p;
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(p)+";"+fncName+"("+(p)+");";
		li.appendChild(a);
		pageNav.append(li);
	}

	var li = document.createElement("li");
	li.className="page-item";
	li.style.cursor="pointer";
	var a = document.createElement("a");
	a.className="page-link";
	a.innerHTML = "Next";
	if(e<pageInfo.last_page){
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(e+1)+";"+fncName+"("+(e+1)+");";
	}
	li.appendChild(a);
	pageNav.append(li);
}




function multiModalControll(){
	$(document).on('show.bs.modal', '.modal', function () {
	    var zIndex = 1040 + (10 * $('.modal:visible').length);
	    $(this).css('z-index', zIndex);
	    setTimeout(function() {
	        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
	    }, 0);
	});
}