<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>


<h1>메인이다</h1>

1.<a href="<%=cp%>/demo/write.action">created</a><br/>
2.<a href="<%=cp%>/demo/save.action">save</a><br/>
3.<a href="<%=cp%>/demo/demo.action">demo</a><br/>
