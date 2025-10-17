<%@page import="model1.board.BoardDTO"%>
<%@page import="model1.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp" %> 
<%
Object userId = session.getAttribute("UserId");
if (userId == null) {
    JSFunction.alertBack("로그인 후 작성해주세요.", out);
    return;
}


//폼 값 받기
String title = request.getParameter("title");
String content = request.getParameter("content");

//폼값을 DTO 객체에 저장
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());
dto.setId(userId.toString());

//DAO 객체를 통해 DB에 DTO 저장
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(dto);
// int iResult = 0;
// for(int i =1; i<= 100; i++){
// 	dto.setTitle(title+ "-" +i);
// 	iResult = dao.insertWrite(dto);
// }
dao.close();

if(iResult == 1){
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
}
%>