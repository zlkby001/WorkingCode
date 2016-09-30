<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>


<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>添加映射关系</title>
     <link href="img/default.css" rel="stylesheet" type="text/css">  
     
<script language="JavaScript">  
  var xmlHttp;
var completeDiv;
var inputField;
var nameTable;
var nameTableBody;
var flag=false;

function createXMLHttpRequest() { //创建xmlHttpRequest对象
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
inputField = document.getElementById("frmchangshang"); //输入框keyword      
nameTable = document.getElementById("name_table");     //table�?
completeDiv = document.getElementById("popup");          //div�?
nameTableBody = document.getElementById("name_table_body");//tbody�?
document.getElementById("popup").style.display="block";   //显示div�?
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
      xmlHttp.onreadystatechange = callback;///////回调函数！！！！
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
      //创建tr，td，span元素
      row =document.createElement("tr");
      cell =document.createElement("td");
      hrefs=document.createElement("a");
      //spans=document.createElement("span");
      //设置cell属�?
      cell.onmouseout = function() {this.className='mouseOut'; flag=false;};
      cell.onmouseover = function() {this.className='mouseOver'; flag=true;};
      cell.setAttribute("bgcolor","#FFFAFA");
      cell.setAttribute("border","0");
      cell.setAttribute("height","20");
          //cell.setAttribute("onmouseover","setflag()");
           //cell.onclick = function() { populateName(this); };
      hrefs.onclick = function() { populateName(this); };
      hrefs.setAttribute("href","javascript:;");
         //将nextNode添加到td
      var txtName = document.createTextNode(nextNode);
      //var txtDuty = document.createTextNode(nextDuty);
      //hrefs.setAttribute("title",nextDuty);
         // cell.appendChild(txtName);
       //装载隐藏数据到span元素
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

function populateName(cell) { //得到cell对象(节点)里的文本�?
//填充数据到web页面，cell---->td对象
   inputField.value = cell.firstChild.nodeValue;
   clearNames();
}
//清除列表数组
function clearNames() {
   var ind = nameTableBody.childNodes.length;
   for (var i = ind - 1; i >= 0 ; i--) {
       nameTableBody.removeChild(nameTableBody.childNodes[i]);
}
   completeDiv.style.border = "none";
}  

     
</script>

 
  </head><body> 
  
  <style type="text/css">
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
</style>
  
  
   
 
<form action="servlet/addMappingServlet" method="post">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		            
		            <tr>
		                <td colspan="2" align="middle" height="30">
		                   <center><strong><font color="#cc0000">增加属性映射关�?/font></strong></center>
		                </td>
		           </tr>
		           <tr>
		                <td colspan="2" align="middle" >
		                   <center><strong><font color="#cc0000">*号为必填部分，其他选填�?/font></strong></center>
		                </td>
		           </tr>
		            
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">软件信息</font></strong>
		                </td>
		            </tr>
		            
		         
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">软件�?</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sname" > <font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">版本�?</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sedition" > 
		        		<font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">厂商:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="smanufacturer" > 
		        	</td>
		        </tr>
		            
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">发布时间:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sissuetime" > 
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">描述信息:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sdescription" > 
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">版权:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="scopyright" > 
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">软件类型:</td>
		        	<td style="padding-left: 2px;">
		        	<SELECT style="WIDTH: 150px" name="sclass"> 
        				<OPTION value=操作系统 selected>操作系统</OPTION> 
        				<OPTION value=网络软件>网络软件</OPTION>
        				<OPTION value=应用软件>应用软件</OPTION>
        				<OPTION value=图形图像>图形图像</OPTION>
        				<OPTION value=多媒�?多媒�?/OPTION>
        				<OPTION value=办公软件>办公软件</OPTION>
        				<OPTION value=游戏娱乐>游戏娱乐</OPTION>
        				<OPTION value=编程开�?编程开�?/OPTION>
        				<OPTION value=杀毒安�?杀毒安�?/OPTION>
      									
        			</SELECT> 	<font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">Hash�?</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="hash" size="50"> 
		        		<font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%"><br></td>
		        	<td style="padding-left: 2px;">
		        		
		        		<br>
		        	</td>
		        </tr>
		        
		        <tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">属性评�?/font></strong>
		                </td>
		            </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">属性名:</td>
		        	<td style="padding-left: 2px;">
		        		<input name="aname" class="InputText" id="frmchangshang" style="width:250px;" onBlur="DisSelect();" onKeyUp="findNames();" size="50" maxlength="100">
                    &nbsp;<span class="style1">
                    <font color="#cc0000">*</font>
                    <br>提示：输入属性名的关键字，系统将自动查找填写
         			</span>
                    <div style="position:absolute;top:-1px;left:0;width:250px;" id="popup">
                        <table id="name_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0" style="width:110px;">           
                            <tbody id="name_table_body" style="width:110px;font-size:12px">
                            </tbody>
                        </table>
                    </div>
                    
		        	</td>
		        </tr>	 
		        
		               
		        
		        <tr> 
                    <td style="padding-left: 10px;" width="20%">属性值：</td>
           		    <td style="padding-left: 2px;">           		    	
           		    	<SELECT style="WIDTH: 150px" name="avalue"> 
        				<OPTION value=EAL1 selected>EAL1</OPTION> 
        				<OPTION value=EAL2>EAL2</OPTION>
        				<OPTION value=EAL3>EAL3</OPTION>
        				<OPTION value=EAL4>EAL4</OPTION>
        				<OPTION value=EAL5>EAL5</OPTION>
        									
        			</SELECT> 
        			<font color="#cc0000">*</font>
                    </td>
                </tr>
                
               
                
                         
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="30%"></td>
   					   			<td>

   					   			<table>
   					   			<tbody><tr>
   					   			<td style="padding-left: 0px;" align="left" width="33%">
   					   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   					   			<input value="   提交   "  type="submit"></td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>					       
		   </form>     
   
  
  </body></html>

