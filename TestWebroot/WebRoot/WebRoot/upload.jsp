<%@ page contentType="text/html;charset=gb2312"%>
<html>
<title><%= application.getServerInfo() %></title>
<body>
�ϴ��ļ�����Ӧ��ʾ��
<form action="doUpload.jsp" method="post" enctype="multipart/form-data">
<%-- ����enctype��multipart/form-data���������԰��ļ��е�������Ϊ��ʽ�����ϴ���������ʲô�ļ����ͣ������ϴ���--%>
��ѡ��Ҫ�ϴ����ļ�<input type="file" name="upfile" size="50">
<input type="submit" value="�ύ">
</form>
</body>
</html>
