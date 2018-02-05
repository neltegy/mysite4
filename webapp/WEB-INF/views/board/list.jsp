<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		
		<!-- /header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- /navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board/list" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${userboard_vo_list }" var="volist" varStatus="status">
					<tr>
						<c:if test="${param.kwd eq null }"> <!-- 검색창이 null일때 -->
						<c:choose>
							<c:when test="${param.choice_pageno >= 2 }">
								<td>${(postnum+1) - (((param.choice_pageno-1)*10)+status.count) }</td> <!-- postnum == 12 countpage == 1 -->
							</c:when>
							<c:otherwise>
								<td>${(postnum+1) - status.count }</td> <!-- 차례대로 번호순서를 부여해주려고 db와는 상관없는 status값을 사용함 -->
																<!-- postnum은 글의 갯수 -->
							</c:otherwise>
						</c:choose>
						</c:if>
						
						<td><a href="${pageContext.request.contextPath }/board/view?boardno=${volist.boardno }&hit=${volist.hit}">${volist.title }</a></td><!--타이틀이 안보이면-->
																												   <!-- null인 상태 -->
						<td>${volist.name }</td>
						<td>${volist.hit }</td>
						<td>${volist.reg_date }</td>
						<td>
							<c:choose>
								<c:when test="${empty authUser }">
								
								</c:when>
								<c:otherwise> <!-- 로그인한사용자들 -->
									<c:choose>
										<c:when test="${authUser.no eq volist.user_no }"> <!-- 자기가 쓴글이 맞을때 -->
											<a href="${pageContext.request.contextPath }/board/delete?boardno=${volist.boardno }" class="del">삭제</a>
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					</c:forEach>
					
				</table>
				
				<div class="pager">
					<ul>
						<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${1 }">◀◀</a></li>
						
						<c:choose>
							<c:when test="${param.choice_pageno eq 1}">
								<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${param.choice_pageno }">◀</a></li><!-- 마지막페이지초과 -->
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${param.choice_pageno-1 }">◀</a></li><!-- 다음으로가려는페이지 -->
							</c:otherwise>
						</c:choose>
						
						<c:forEach items="${page }" var="pageno" varStatus="status"> <!-- 페이지번호만드는 반복문 -->
							<li 
								<c:choose>
									<c:when test="${pageno+1 eq param.choice_pageno }">
										class="selected"
									</c:when>
								</c:choose>
								
								><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${pageno + 1}">${pageno + 1}</a></li>
						</c:forEach>
						
						<c:choose>
							<c:when test="${param.choice_pageno eq total_page_num}"> <!-- page.size() -->
								<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${param.choice_pageno }">▶</a></li><!-- 마지막페이지초과 -->
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${param.choice_pageno+1 }">▶</a></li><!-- 다음으로가려는페이지 -->
							</c:otherwise>
						</c:choose>
						
						<li><a href="${pageContext.request.contextPath }/board/list?choice_pageno=${total_page_num }">▶▶</a></li> <!-- 최대 페이지 -->
					</ul> <!-- rejsppageno -->
					
					<!-- <ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li><a href="">2</a></li>
						<li class="selected">3</li>
						<li><a href="">4</a></li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul> -->
					
				</div>
				
				<c:choose>
					<c:when test="${empty authUser }">
						
					</c:when>
					<c:otherwise>
						<div class="bottom">
						<a href="${pageContext.request.contextPath }/board/writeform" id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>
								
			</div>
		</div>
		
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div>
</body>
</html>