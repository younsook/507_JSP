<%@page import="common.Person"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
ArrayList<String> lists = new ArrayList<String>();
lists.add("리스트");
lists.add("컬렉션");
session.setAttribute("lists", lists);

//person 
ArrayList<Person> pson = new ArrayList<Person>();
Person p = new Person("최부산", 31);
pson.add(p);

pson.add(new Person("안중근", 31));
pson.add(new Person("김부산", 41));
pson.add(new Person("이부산", 51));
session.setAttribute("pson", pson);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session 영역</title>
</head>
<body>
<h2>페이지 이동 후 session 영역의 속성 일기</h2>
<a href="SessionLocation.jsp">SessionLocation.jsp 바로가기</a>
</body>
</html>