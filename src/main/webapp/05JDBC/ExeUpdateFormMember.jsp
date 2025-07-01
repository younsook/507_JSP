<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    String result = request.getParameter("result");
    if ("success".equals(result)) {
%>
    <p style="color: green; font-weight: bold;">
        ✅ 회원이 성공적으로 등록되었습니다!
    </p>
<%
    }
%>

<h2>회원 등록</h2>
<form action ="ExeUpdateMember.jsp" method="post">
<table>
	<tr>
		<td width="100px">id</td>
		<td><input type ="text" name="id"></td>
	</tr>
	<tr>
		<td>pass</td>
		<td><input type ="text" name="pass"></td>
	</tr>
	<tr>
		<td>name</td>
		<td><input type ="text" name="name"></td>
	</tr>
	<tr>
		<td height="50px"></td>
		<td><input type ="submit" name="POST" value="회원 등록 전송" style="width:176px"></td>
	</tr>
</table>
<br>
</form>
<form action ="ExeQuery.jsp" method="get">
<table>
	<tr>
		<td width="100px"></td>
		<td><input type ="submit" value="회원 목록 조회" style="width:176px"></td>
	</tr>
	
</table>
</form>
</body>
</html>
