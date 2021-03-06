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
<title>게 시 판</title>

<link rel="stylesheet" href="<%=cp %>/sfile/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp %>/sfile/css/list.css" type="text/css">

<script type="text/javascript">

	function searchData(){
		var f = document.searchForm;
		
		f.action = "<%=cp%>/file/list.action";
		f.submit();
		
	}

</script>

</head>
<body>

<div id="bbsList">

	<div id="bbsList_title">
	게 시 판
	</div>
	
	<div id="bbsList_header">
		<div id="leftHeader">
			<form action="" name="searchForm" method="post">
				<select name="searchKey" class="selectField">
					<option value="subject">제목</option>
				</select>
				<input type="text" name="searchValue" class="textField"/>
				<input type="button" value="검 색" class="btn2" onclick="searchData()"/>
			</form>	
		</div>
		<div id="rightHeader">
			<input type="button" value="글 올리기" class="btn2" onclick="javascript:location.href='<%=cp%>/file/write.action';"/>
		</div>
	</div>
	
	<div id="bbsList_list">
		<div id="title">
			<dl>
				<dt class="num">번호</dt>
				<dt class="subject">제목</dt>
				<dt class="originalFileName">originalFileName</dt>
				<dt class="saveFileName">saveFileName</dt>
			</dl>
		</div>
		
		<div id="lists">
			<c:forEach var="dto" items="${lists}">
			<dl>
				<dd class="num">${dto.listNum }</dd>
				<dd class="subject">
				<a href="${dto.urlFile }">
				${dto.subject }
				</a>
				</dd>
				<dd class="originalFileName">${dto.originalFileName }</dd>
				<dd class="saveFileName">${dto.saveFileName }</dd>
			</dl>
			</c:forEach>
		</div>
		
		<div id="footer">
			<p>
				<c:if test="${totalDataCount!=0 }">
					${pageIndexList}
				</c:if>
				<c:if test="${totalDataCount==0 }">
					등록된 게시물이 없습니다.
				</c:if>
			</p>
		</div>

	</div>
	
</div>
</body>
</html>