<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %>
<%
String num = request.getParameter("num");
BoardDTO dto = new BoardDTO();
BoardDAO dao = new BoardDAO(application);
//DB에서 해당 번호의 게시글을 가져옴
dto = dao.selectView(num);  // ← 게시물 작성자의 id를 가져오기 위함

//세션에서 로그인 사용자 ID
String sessionId = session.getAttribute("UserId").toString();


int delResult = 0;

if(sessionId.equals(dto.getId())){
	dto.setNum(num);
	delResult = dao.deletePost(dto);
	dao.close();
	
	if(delResult == 1){
		JSFunction.alertLocation("삭제되었습니다.", "List.jsp", out);
	}else {
		JSFunction.alertBack("삭제에 실패하였습니다.", out);
	}
}else{
	JSFunction.alertBack("본인만 삭제할 수 있습니다.", out);
	return;
}
%>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>