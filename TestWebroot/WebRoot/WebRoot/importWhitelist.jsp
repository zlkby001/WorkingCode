<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*" %>
   <%@ page import="java.util.*,java.util.Iterator,cn.ac.sklois.imm.mappings.*,com.jspSmartUpload.upload.*,java.text.SimpleDateFormat,java.util.Date;"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>


<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//String ics_id = request.getParameter("ics_id");
	UserBean a = (UserBean)request.getSession(true).getAttribute("a");
	int userid = Integer.parseInt(a.getID());
	System.out.println("breakpoint");
String id="1";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>添加属性映射关系</title>
     <link href="img/default.css" rel="stylesheet" type="text/css"> 
     <link href="extjs3/resources/css/ext-all.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/ext-all-notheme.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/reset-min.css" rel="stylesheet" type="text/css">
     <link href="extjs3/resources/css/xtheme-blue.css" rel="stylesheet" type="text/css">
 <script src="extjs3/adapter/ext/ext-base.js" type="text/javascript"></script>    
 <script src="extjs3/ext-lang-zh_CN.js" type="text/javascript"></script> 
 <script type="text/javascript" src="extjs3/ext-all.js"> </script> 
 <script type="text/javascript" src="extjs3/ext-all-debug.js"> </script>     
<script language="JavaScript">  
  var xmlHttp;
var completeDiv;
var inputField;
var nameTable;
var nameTableBody;
var flag=false;
function createXMLHttpRequest() { //构建xmlHttpRequest
   if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
   else if (window.XMLHttpRequest) {
         xmlHttp = new XMLHttpRequest();               
   }
}

function setflag(){  
   flag = true;
}
       
function DisSelect()
{
   if(flag==false)
      document.getElementById("popup").style.display="none";
}

function initVars() {
inputField = document.getElementById("frmchangshang"); //keyword      
nameTable = document.getElementById("name_table");     //table
completeDiv = document.getElementById("popup");          //div
nameTableBody = document.getElementById("name_table_body");//tbody
document.getElementById("popup").style.display="block";   //div
}

function findNames() {  

    initVars();         
    if (inputField.value.length > 0)
    {
      createXMLHttpRequest();
      //var url = "servlet/SearchServlet?keyword="+inputField.value;
      //xmlHttp.open("GET", url, true);
      var url = "servlet/SearchServlet?keyword="+inputField.value;
      
      xmlHttp.open("GET", url, true);
      xmlHttp.onreadystatechange = callback;//////
      xmlHttp.send(null);
    }else{                 
      clearNames();       
    }
}

function callback() {
if (xmlHttp.readyState == 4)
{
    if (xmlHttp.status == 200)
    {
       try
          {
          		//var doc = new ActiveXObject("MSxml2.DOMDocument")
				//doc.loadXML(xmlhttp.responseText);
				
             var name = xmlHttp.responseXML.getElementsByTagName("name")[0].firstChild.data;
				//var response=doc.getRootElement();
			
				//List mpl=response.getChildren();
				
           }catch(e){
             
             document.getElementById("popup").style.display="none";
             clearNames();
          }
       
       setNames(xmlHttp.responseXML.getElementsByTagName("content"));
     }
else if (xmlHttp.status == 204)
         {
           clearNames();
          }
}
}
       
function setNames(the_names) {           
    clearNames();
    var size = the_names.length;    
    setOffsets();
    var row,cell,spans,hrefs;
    for (var i = 0; i < size; i++) {
      
      var e = the_names[i];
      
      var nextNode=e.getElementsByTagName("name")[0].firstChild.data;
      //var nextDuty=e.getElementsByTagName("duty")[0].firstChild.data;
      //
      row =document.createElement("tr");
      cell =document.createElement("td");
      hrefs=document.createElement("a");
      //spans=document.createElement("span");
      //
      cell.onmouseout = function() {this.className='mouseOut'; flag=false;};
      cell.onmouseover = function() {this.className='mouseOver'; flag=true;};
      cell.setAttribute("bgcolor","#FFFAFA");
      cell.setAttribute("border","0");
      cell.setAttribute("height","20");
          //cell.setAttribute("onmouseover","setflag()");
           //cell.onclick = function() { populateName(this); };
      hrefs.onclick = function() { populateName(this); };
      hrefs.setAttribute("href","javascript:;");
         //
      var txtName = document.createTextNode(nextNode);
      //var txtDuty = document.createTextNode(nextDuty);
      //hrefs.setAttribute("title",nextDuty);
         // cell.appendChild(txtName);
       //
      hrefs.appendChild(txtName);
      cell.appendChild(hrefs);
     row.appendChild(cell);
      nameTableBody.appendChild(row);
     }
}

function setOffsets() {
     var end = inputField.offsetWidth;
     var left = calculateOffsetLeft(inputField);
     var top = calculateOffsetTop(inputField) + inputField.offsetHeight;
     completeDiv.style.border = "black 1px solid";
     completeDiv.style.left = left + "px";
     completeDiv.style.top = top + "px";
     nameTable.style.width=inputField.offsetWidth;
        }
       
function calculateOffsetLeft(field) {
    return calculateOffset(field, "offsetLeft");
   }

   function calculateOffsetTop(field) {
    return calculateOffset(field, "offsetTop");
   }

function calculateOffset(field, attr) {
       var offset = 0;
    while(field) {
     offset += field[attr];
     field = field.offsetParent;
   }
   return offset;
}

function populateName(cell) { //
//
   inputField.value = cell.firstChild.nodeValue;
   clearNames();
}
//
function clearNames() {
   var ind = nameTableBody.childNodes.length;
   for (var i = ind - 1; i >= 0 ; i--) {
       nameTableBody.removeChild(nameTableBody.childNodes[i]);
}
   completeDiv.style.border = "none";
}  

function upload(){
	     document.forms[0].action= "servlet/uploadFileServlet "; 
     	document.forms[0].submit(); 
	}
function doup(){
	document.forms[0].action = "servlet/uploadwhitelistServlet";
	document.forms[0].submit();
}
function checkForm(form){
		//alert("yes");
     	var s=fr1.upfile1.value;
     	//if(form.sissuetime.value==""){
		//confirm("请输入发布时间！");
		//return false;	
	//}
     	//if(fr.upfile.value=="")
     	//{
     		//confirm("请上传文件!");
     		//return false;
     	//}
     	if(s.indexOf(".enc")==-1)
     	{
     		confirm("请选择正确的文件格式!")
     		 
     		return false;
     		
     	}
     	
     	
     	//==============导入知识库=======================
     	
     	var val = confirm("您确定要导入此白名单文件吗??");
		if (val) {
		document.forms[0].action = "servlet/uploadwhitelistServlet";
		document.forms[0].submit();
     	 
					    //var result = ajaxSyncCall('servlet/uploadknowledgefromxmlServlet?ics_id=' + ics_id, null);
		    //if (result.success) {
		    	//thisGrid.getStore().reload();		
		    	//alert("已成功导入该文件!");
		    //} else {
		    	//alert(result.errorMsg);
		    //}
           Ext.Msg.wait('文件导入中...', '稍等',
		   {
			text: '进度',
			scope: this
			});
        
		} 
     	  
     	//==============导入知识库=======================
     	//==============================================
     	
     	//==============================================
     	return true;
 }

 
 function ajaxSyncCall(urlStr, paramsStr) {
	var obj;
	if (window.ActiveXObject) {
		obj = new ActiveXObject('Microsoft.XMLHTTP');
	} else if (window.XMLHttpRequest) {
		obj = new XMLHttpRequest();
	}
	obj.open('POST', urlStr, false);
	obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	obj.send(paramsStr);
	var result = Ext.util.JSON.decode(obj.responseText);
	return result;
}   
</script>

  </head><body>
<!-- <style type="text/css">
div{
   	
  	
     overflow-y:scroll;
     display:none;
}



body{

   font-family:arial,sans-serif
   }
.style1
{
color: #FF0000
}
a{
display:block;
width:250px;
height:22px;
line-height:22px;
}
a:link,a:visited{
text-decoration:none;
color: #000022;
}
a:hover{
color: #FFFAFA;
}
.mouseOver
{
font-size:12px;

background:#0000CC;
color: #FFFAFA;
}

.mouseOut
{
font-size:12px;
background: #FFFAFA;
color: #000022;
}
</style> -->
 
   <form name="fr1" id="form2" action="" method="post" enctype="multipart/form-data" onsubmit="return checkForm(this);">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="1116" height="191">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		            
		            <tr>
		                <td colspan="2" align="middle" height="30">
		                   <center><strong><font color="#cc0000">导入白名单文件</font></strong></center>
		                </td>
		           </tr>
		           <tr>
		                <td colspan="2" align="middle" >
		                   <center><strong><font color="#cc0000">请选择正确文件格式！</font></strong></center>
		                </td>
		           </tr>
		            
		        	
		            
		          <tr> 
                <td height="20"></td>
                </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">选择要上传的白名单文件:</td>
		        	<td style="padding-left: 2px;">
		        		<input id = "fileText1" type="file" name="upfile1" size="50">
		        		<font color="#cc0000">*</font>
		        	</td>


  					 
		        </tr>
	
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="20%"></td>
   					   			<td>

   					   			<table>
   					   			<tbody><tr>
   					   			<td style="padding-left: 0px;" align="left" width="33%">
   					   			<input value="  提交文件   "  type="submit"  >
   					   			<!-- <input value="  提交   "  type="button" onclick="daoru();"  > -->
   					   			</td>		
   					   			<td style="padding-left: 0px;" align="left" width="33%">
   					   			<input type="button" value="  返回   " onclick="history.back();">	
   					   		
   					   			</td>                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>

    </form>	   
   
  </body></html>

