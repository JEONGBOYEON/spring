<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

리스트겸 파일 다운로드<br/>
<a href="<%=cp%>/file/download.action?saveFileName=${saveFileName}&originalFileName=${originalFileName}">
${originalFileName }
</a>
<br/><br/>
파일 보기<br/>
<a href="<%=cp%>/file/downView.action?saveFileName=${saveFileName}&originalFileName=${originalFileName}">
${originalFileName }
</a>

view  
</body>
</html>