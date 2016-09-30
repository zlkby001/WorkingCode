<%@page language="java" contentType="text/html; charset=GBK"     pageEncoding="UTF-8" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script language="JavaScript"> 
function clickreturn()
{
out.println(filename);
	opener.location.href="doUpload.jsp?uploadfilepath="+filename;
  window.close();
}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>	
<base href="<%=basePath%>">
<title>修改结果</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
BODY {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none;padding-top:80px;}
TABLE {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
TD {FONT-SIZE: 9pt; COLOR: #2D54A1; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
BODY {SCROLLBAR-HIGHLIGHT-COLOR: buttonface; SCROLLBAR-SHADOW-COLOR: buttonface; SCROLLBAR-3DLIGHT-COLOR: buttonhighlight; SCROLLBAR-TRACK-COLOR: #eeeeee; BACKGROUND-COLOR: #ffffff}
A {FONT-SIZE: 9pt; COLOR: #ff0000; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: none}
A:hover {FONT-SIZE: 9pt; COLOR: #ff0000; LINE-HEIGHT: 16pt; FONT-FAMILY: "Tahoma", "宋体"; TEXT-DECORATION: underline}
H1 {FONT-SIZE: 14PX; FONT-FAMILY: "Tahoma", "宋体"}
hr{color:#ccc;}
</style>

<meta content="MSHTML 6.00.2800.1458" name="GENERATOR"></head><body>
<div style="border: 1px solid rgb(221, 221, 221); margin: auto; padding: 30px 45px 20px 20px; width: 600px;">
<table cepadding="0" align="center" border="0" cellspacing="0" width="600">
  <tbody>
  <tr colspan="2">
    <td align="middle" valign="top"> 
    </td><td>

    </td><td>
      <h1><img src="img/result.gif" width="400" height="66"></h1>
	  <hr size="1" noshade="noshade">
      <p>☉ 操作结果：</p>
      <ul>
        <li>
        	<%
//定义上载文件的最大字节
int MAX_SIZE = 102400 * 102400;
// 创建根路径的保存变量
String rootPath;
//声明文件读入类
DataInputStream in = null;
FileOutputStream fileOut = null;
//取得客户端的网络地址
String remoteAddr = request.getRemoteAddr();
//获得服务器的名字
String serverName = request.getServerName();

//取得互联网程序的绝对地址
String realPath = request.getRealPath(serverName);
realPath = realPath.substring(0,realPath.lastIndexOf("\\"));
//创建文件的保存目录
rootPath = realPath + "\\upload\\";
//取得客户端上传的数据类型
String contentType = request.getContentType();
try{
if(contentType.indexOf("multipart/form-data") >= 0){
//读入上传的数据
in = new DataInputStream(request.getInputStream());
int formDataLength = request.getContentLength();
if(formDataLength > MAX_SIZE){
out.println("<p>上传的字节数不可以超过 " + MAX_SIZE + "</p>");
return;
}
//保存上传文件的数据
byte dataBytes[] = new byte[formDataLength];
int byteRead = 0;
int totalBytesRead = 0;
//上传的数据保存在byte数组
while(totalBytesRead < formDataLength){
byteRead = in.read(dataBytes,totalBytesRead,formDataLength);
totalBytesRead += byteRead;
}
//根据byte数组创建字符串
String file = new String(dataBytes);
//out.println(file);
//取得上传的数据的文件名
String saveFile = file.substring(file.indexOf("filename=\"") + 10);
saveFile = saveFile.substring(0,saveFile.indexOf("\n"));
saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
int lastIndex = contentType.lastIndexOf("=");
//取得数据的分隔字符串
String boundary = contentType.substring(lastIndex + 1,contentType.length());
//创建保存路径的文件名
String fileName = rootPath + saveFile;
//out.print(fileName);
int pos;
pos = file.indexOf("filename=\"");
pos = file.indexOf("\n",pos) + 1;
pos = file.indexOf("\n",pos) + 1;
pos = file.indexOf("\n",pos) + 1;
int boundaryLocation = file.indexOf(boundary,pos) - 4;
//out.println(boundaryLocation);
//取得文件数据的开始的位置
int startPos = ((file.substring(0,pos)).getBytes()).length;
//out.println(startPos);
//取得文件数据的结束的位置
int endPos = ((file.substring(0,boundaryLocation)).getBytes()).length;
//out.println(endPos);
//检查上载文件是否存在
File checkFile = new File(fileName);
if(checkFile.exists()){
out.println("</p>" + saveFile + "test文件已存在</p>");
}
//检查上载文件的目录是否存在
File fileDir = new File(rootPath);
if(!fileDir.exists()){
fileDir.mkdirs();
}
//创建文件的写出类
fileOut = new FileOutputStream(fileName);
//保存文件的数据
fileOut.write(dataBytes,startPos,(endPos - startPos));
fileOut.close();
out.println(saveFile + "文件成功上载</p>");
}else{
String content = request.getContentType();
out.println("<p>上传的数据类型不是multipart/form-data</p>");
}
}catch(Exception ex){
throw new ServletException(ex.getMessage());
}
%>

        </li>  
      </ul>
      
      
      
      
      <p>☉<a href="#" onclick = "javascript:clickreturn()" >返回</a>
   					   					
   					   				
   					   					
   					   				
   					   				
   					   			</p>
      <hr size="1" noshade="noshade">
      <p>☉ 如果您还有任何疑问、意见、建议、咨询，请<a href="mailto:XXX@gmail.com">联系管理员</a> 
      <br>
      &nbsp;&nbsp;&nbsp;<br>
      </p></td></tr></tbody></table>
	  </div>
</body></html>