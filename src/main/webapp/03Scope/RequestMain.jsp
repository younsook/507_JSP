<%@page import="common.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("requestString", "request 영역의 문자열");
request.setAttribute("requestPerson", new Person("안중근", 31));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>request 영역</title>
</head>
<body>
<h2>request 영역의 속성값 삭제하기</h2>
<%
request.removeAttribute("requestString");
request.removeAttribute("requestInteger");
%>
<h2>request 영역의 속성값 읽기</h2>
<%
Person rPerson = (Person)(request.getAttribute("requestPerson"));
%>
<ul>
<li>String 객체: <%= request.getAttribute("requestString") %> </li>
<li>Person 객체: <%= rPerson.getName() %>, <%= rPerson.getAge() %></li>
</ul>
<h2>포워드된 페이지에서 request 영역 속성값 읽기</h2>
<%
 request.getRequestDispatcher(
"RequestForward.jsp?paramHan=한글&paramEng=English").forward(request, response); 
//현재 실행한 페이지 url만 표시 forward 된 주소는 노출안됨.
// response.sendRedirect("RequestForward.jsp?paramHan=h&paramEng=English"); 
//sendRedirect : RequestForward.jsp로 넘어가는 값이 없어서 오류, 이 url로 변경됨.
%>
</body>
</html>