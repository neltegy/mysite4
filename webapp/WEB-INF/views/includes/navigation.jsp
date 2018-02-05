<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<div id="navigation">
			<ul>
				<li><a href="${pageContext.request.contextPath }/main">정호윤</a></li>
				<li><a href="${pageContext.request.contextPath }/gb/list">방명록</a></li>
				<li><a href="${pageContext.request.contextPath }/board/list">게시판</a></li>
				<li><a href="${pageContext.request.contextPath }/chat?a=chatform">채팅방</a></li>
			</ul>
</div>