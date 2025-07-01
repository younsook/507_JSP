<%@page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement" %>
<%@page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Board 조회</h2>
	<%
	JDBConnect jdbc = new JDBConnect();
	
	String sql = "SELECT num, title, content, id, postdate from Board";
	Statement stmt = jdbc.getCon().createStatement();
	
	ResultSet rs = stmt.executeQuery(sql);
	
	while (rs.next()){
		String num = rs.getString("num");
		String title = rs.getString("title");
		String content = rs.getString("content");
		String id = rs.getString("id");
		java.sql.Timestamp postdate = rs.getTimestamp("postdate");
		
		out.println(String.format("%s %s %s %s %s", num, title, content, id, postdate) + "<br/>");
	}
	
	rs.close();
    stmt.close();
    jdbc.close();
	%>
	<br>
	
	<form action="ExeUpdateFormBoard.jsp" method="get">
    <input type="submit" value="Board 입력 화면으로 이동">
	</form>
	
	
</body>
</html>