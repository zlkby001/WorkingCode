<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="cn.ac.sklois.imm.admin.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
     <title>增加新属性</title>
     <link href="img/default.css" rel="stylesheet" type="text/css">   
     
 
<script language="JavaScript">  
    
 
function checkForm(){
	var str=document.getElementById("aclass").value;
 	if(str!=" "){
		return true;
	}else{
		confirm("请选择属性等级！");
		return false;
	}
 }
     
     
</script>     
     
     
     
     
  </head><body>  
 
<form action="servlet/addAttServlet" method="post" onsubmit="return checkForm();">
  <table style="border-collapse: collapse;" align="center" border="0" bordercolor="#77aa33" cellpadding="2" cellspacing="4" width="90%">
		        <tbody>
		        
		            <tr>
		                <td colspan="2" align="middle" height="40">
		                   
		                </td>

		            </tr>
		        	<tr>
		                <td colspan="2" align="middle" height="30">
		                   <strong><font color="#cc0000">新属性</font></strong>
		                </td>
		            </tr>
		            
		            
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">属性ID:</td>
		        	<td style="padding-left: 2px;">
		        		<input type="text" name="aid" size="16"> 
		        	</td>

		        </tr>
		        
		        <!--begin  -->   
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">属性名:</td>
		        	<td style="padding-left: 2px;"><input type="text" name="aname" size="50" ></td>
		        </tr>
		        <!--end  -->
		        
		        <tr>
		        	<td style="padding-left: 10px;" width="20%">等级:</td>
		        	<td style="padding-left: 2px;">
		        	<SELECT style="WIDTH: 150px" name="aclass"> 
        				<OPTION value=" " selected>请选择...</OPTION>
        				<OPTION value=1>1</OPTION> 
        				<OPTION value=2>2</OPTION>
        				<OPTION value=3>3</OPTION>
        				<OPTION value=4>4</OPTION>
        				<OPTION value=5>5</OPTION>
        				<OPTION value=6>6</OPTION>
        				<OPTION value=7>7</OPTION>
        				<OPTION value=8>8</OPTION>
        				<OPTION value=9>9</OPTION>
        				<OPTION value=10>10</OPTION>
        				
        			</SELECT>
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
   					   			<td style="padding-left: 0px;" align="center" width="33%"><input value="   提交   "  type="submit" ></td>			                				                
			   	   			 </tr></tbody></table></td>      				              						                			                
              				</tr>

              				
                
                </tbody>
                </table>					       
		   </form>     
   
  
  </body></html>
