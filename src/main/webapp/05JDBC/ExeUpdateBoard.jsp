<%@page import="java.sql.PreparedStatement"%>
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
<li>전송된 값 title : <%= request.getParameter("title") %></li>
<li>전송된 값 content : <%= request.getParameter("content") %></li>
<li>전송된 값 id : <%= request.getParameter("id") %></li>
</ul>

	<h2>회원(전송된 값) 추가 테이블(executeUpdate() 사용)</h2>
	<%

		JDBConnect jdbc = new JDBConnect();
	
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		
		String sql = "INSERT INTO board(title, content, id, postdate) VALUES (?, ?, ?, NOW())";
		PreparedStatement psmt = jdbc.getCon().prepareStatement(sql);
		psmt.setString(1, title);
		psmt.setString(2, content);
		psmt.setString(3, id);
		
		int inResult = psmt.executeUpdate();
		out.println(inResult + "행이 입력되었습니다.");
		
		psmt.close();
		jdbc.close();
		response.sendRedirect("ExeUpdateFormBoard.jsp?result=success");
		
	%>


</body>
</html>