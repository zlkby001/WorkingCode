<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*" %>
   <%@ page import="java.util.ArrayList,java.util.Iterator,cn.ac.sklois.imm.mappings.*,java.text.SimpleDateFormat,java.util.Date;"   %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>


<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<base href="<%=basePath%>">

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>添加属性映射关系</title>
     <link href="img/default.css" rel="stylesheet" type="text/css">  
     
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


function checkForm(form){
	if(form.sname.value==""){
		confirm("请输入软件名！");
		return false;	
	}
	if(form.filename.value==""){
		confirm("请输入文件名！");
		return false;	
	}
	if(form.hash.value == ""){
		confirm("请输入完整性值！");
		return false;
	}
	if(form.sissuetime.value == ""){
		confirm("请输入发布时间！");
		return false;
	}
	return true;
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
  
  
   
 
<form action="servlet/addMappingServlet" method="post" onsubmit="return checkForm(this);">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="100%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		            
		            <tr>
		                <td colspan="2" align="middle" height="30">
		                   <center><strong><font color="#cc0000">增加完整性元数据</font></strong></center>
		                </td>
		           </tr>
		           <tr>
		                <td colspan="2" align="middle" >
		                   <center><strong><font color="#cc0000">*号为必填部分，其他选填！</font></strong></center>
		                </td>
		           </tr>
		            
		        	
		            
		         <tr>
		        	<td style="padding-left: 10px;" width="10%">软件类型:</td>
		        	<td style="padding-left: 2px;">
		        	<SELECT style="WIDTH: 150px" name="sclass"> 
        				<OPTION value="1" selected>操作系统</OPTION> 
        				<OPTION value="2">网络软件</OPTION>
        				<OPTION value="3">系统工具</OPTION>        				
        				<OPTION value="4">应用软件</OPTION>
        				<OPTION value="5">图形图像</OPTION>
        				<OPTION value="6">多媒体</OPTION>
        				<OPTION value="7">办公软件</OPTION>
        				<OPTION value="8">游戏娱乐</OPTION>
        				<OPTION value="9">编程开发</OPTION>
        				<OPTION value="10">杀毒安全</OPTION>
        				<OPTION value="11">系统补丁</OPTION> 
        				<OPTION value="12">软件补丁</OPTION>     									
        			</SELECT> 	<font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">所属软件包:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sname" > <font color="#cc0000">*</font>
		        	</td>
		        </tr>		        
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">文件名:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="filename" > <font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">版本号:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sedition" > 
		        	</td>
		        </tr>
             <%
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				String	issuetime = df.format(new Date());
				%>
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">发布时间:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sissuetime" value=<%=issuetime%>> 
		        		<font color="#cc0000">*</font>
		        	</td>
		        </tr>
<!--	
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">厂商:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="smanufacturer" > 
		        	</td>
		        </tr>
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">版权:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="scopyright" > 
		        	</td>
		        </tr>
		        
		        -->
		        	        
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="10%">完整性值：</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="hash" size="80"> 
		        		<font color="#cc0000">*</font>
		        	</td>
		        </tr>
		        
	        
		        
<!--			        <%
		        	MappingService ms=new MappingService();
		        	ArrayList al=ms.ListAttIDs(-1,null,null);
		         %>
		        
		        
	        <tr>
		        	<td style="padding-left: 10px;" width="10%">软件用途:</td>
		        	<td style="padding-left: 2px;">
                    	<SELECT style="WIDTH: 380px" name="aid"> 
                    	<%
		        		for(int i=0;i<al.size();i++){
		        			AttIDtoName a=(AttIDtoName)al.get(i);
		        			if(a.getAttributeID()==0)
		        				out.print("<OPTION value='"+a.getAttributeID()+"' selected>"+a.getAttributeName()+"</OPTION>");
		        			else
		        				out.print("<OPTION value='"+a.getAttributeID()+"'>"+a.getAttributeName()+"</OPTION>");		        			
		        		}
		         		%>
                    	</SELECT> 	
                    	<font color="#cc0000">*</font>
                    
		        	</td>
		        </tr>	 
		-->        
		               
		        
		        <tr> 
                    <td style="padding-left: 10px;" width="10%">是否可信:</td>
           		    <td style="padding-left: 2px;">           		    	
           		    	<SELECT style="WIDTH: 150px" name="avalue"> 
        				<OPTION value=1 selected>可信</OPTION> 
        				<OPTION value=0>不可信</OPTION>				
        			</SELECT> 
        			<font color="#cc0000">*</font>
                    </td>
                </tr>
                
               
               <tr>
		        	<td style="padding-left: 10px;" width="10%">描述信息:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="sdescription" size=65> 
		        	</td>
		        </tr>
                
                         
                <tr> 
                <td height="20"></td>
                </tr>
                
   					   			
   					   			<tr bgcolor="#9AADCD">
   					   			<td width="20%">&lt;</td>
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

