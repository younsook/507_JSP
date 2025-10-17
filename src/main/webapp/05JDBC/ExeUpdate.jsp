<%@ page import="java.sql.PreparedStatement" %>
<%@page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JDBC</title>
</head>
<body>
	<h2>회원 추가 테이블(executeUpdate() 사용)</h2>
	<%
		JDBConnect jdbc = new JDBConnect();
	
		String id = "test2";
		String pass = "1112";
		String name = "테스트2회원";
		
		String sql = "INSERT INTO member(id, pass, name, regidate) VALUES (?, ?, ?, sysdate())";
		PreparedStatement psmt = jdbc.getCon().prepareStatement(sql);
		psmt.setString(1, id);
		psmt.setString(2, pass);
		psmt.setString(3, name);
		
		int inResult = psmt.executeUpdate();
		out.println(inResult + "행이 입력되었습니다.");
		
		psmt.close();
		jdbc.close();
		
	%>
</body>
</html>