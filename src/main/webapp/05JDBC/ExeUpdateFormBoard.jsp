<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board 입력</title>
</head>
<body>
<%
    String result = request.getParameter("result");
    if ("success".equals(result)) {
%>
    <p style="color: blue; font-weight: bold;">
        ✅ 입력이 성공적으로 등록되었습니다!
    </p>
<%
    }
%>
<h2>Board 입력</h2>
<form action ="ExeUpdateBoard.jsp" method="post">
<table>
	<tr>
		<td width="100px">title</td>
		<td><input type ="text" name="title"></td>
	</tr>
	<tr>
		<td>content</td>
		<td><textarea name="content" rows="5" cols="40" placeholder="내용을 입력하세요"></textarea></td>
	</tr>
	<tr>
		<td>id</td>
		<td><input type ="text" name="id"></td>
	</tr>
	<tr>
		<td height="50px"></td>
		<td><input type ="submit" name="POST" value="입력 전송" style="width:176px"></td>
	</tr>
</table>
<br>
</form>
<form action ="ExeQueryBoard.jsp" method="get">
<table>
	<tr>
		<td width="100px"></td>
		<td><input type ="submit" value="Board 조회" style="width:176px"></td>
	</tr>
	
</table>
</form>
</body>
</html>

