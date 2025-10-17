<%@ page import="java.sql.PreparedStatement" %>
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
<ul>
<li>전송된 값 id : <%= request.getParameter("id") %></li>
<li>전송된 값 pass : <%= request.getParameter("pass") %></li>
<li>전송된 값 name : <%= request.getParameter("name") %></li>
</ul>

	<h2>회원(전송된 값) 추가 테이블(executeUpdate() 사용)</h2>
	<%

		JDBConnect jdbc = new JDBConnect();
	
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		
		String sql = "INSERT INTO member(id, pass, name, regidate) VALUES (?, ?, ?, sysdate())";
		PreparedStatement psmt = jdbc.getCon().prepareStatement(sql);
		psmt.setString(1, id);
		psmt.setString(2, pass);
		psmt.setString(3, name);
		
		int inResult = psmt.executeUpdate();
		out.println(inResult + "행이 입력되었습니다.");
		
		psmt.close();
		jdbc.close();
		response.sendRedirect("ExeUpdateFormMember.jsp?result=success");
		
	%>



</body>
</html>
