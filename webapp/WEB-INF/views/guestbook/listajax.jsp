<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
	
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<!-- /header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		
		<!-- /navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					
					<%-- <form action="${pageContext.request.contextPath }/gb/api/save" method="post"> --%>
						
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 " /></td>
							</tr>
						</table>
					<!-- </form> -->
					<input id= "btnDel" type="button" value="삭제예제버튼" />
					
					<ul id="listArea">
						
					</ul>
					<input id="btnNext" type="button" value="다음글 5개 가져오기">
					
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div> <!-- /container -->
	
		<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" id="modalPassword"><br>	
					<input type="hidden" name="modalPassword" value="" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	

</body>

<script type="text/javascript">
var page = 1;
	
	$("[type=submit]").on("click",function(){
		//ajax로 방금 보낸데이터로 받아서 up으로해서 추가한다.
		var name = $("[name=name]").val();
		var password = $("[name=password]").val();
		var content = $("[name=content]").val();
		
		$("[name=name]").val("");
		$("[name=password]").val("");
		$("[name=content]").val("");
		
		var guestbookVo = {
			name: name,
			password: password,
			content: content
		}
		
		$.ajax({
			url : "${pageContext.request.contextPath }/gb/api/save",
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(guestbookVo),
			
			dataType : "json",
			success : function(getguestbook){
				/*성공시 처리해야될 코드 작성*/
				render(getguestbook,"up");
			},
			
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}

		});
	});

	$(document).ready(function(){
		
		conajax();
	});
	
	$("#btnNext").on("click",function(){
		page++;
		console.log(page);
		
		conajax();
	});
	
/* 	$("#btn_del").on("click",function(){
		$("#del-pop").hide();
	}); */
	
	
function conajax(){
	$.ajax({
		url : "${pageContext.request.contextPath }/gb/api/list",
		type : "post",
		/* contentType : "application/json",
		data : JSON.stringify(guestbookVo), */
		data : {page: page},
		
		dataType : "json",
		success : function(guestbooklist){
			/*성공시 처리해야될 코드 작성*/
			console.log(guestbooklist);
			
			for(var i = 0 ; i < guestbooklist.length; i++){
				render(guestbooklist[i],"down");
			}
			
		},
		
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}

	});
};

$("ul").on("click",".btn_del_content",function(){ /* 게시물의 삭제버튼들중 하나를 눌렀을 경우 */
	
	var no = $(this).data("no");

	$("#modalNo").val(no);
	$("#del-pop").modal();
	
});

$("#btn_del").on("click",function(){
	var no = $("#modalNo").val();
	var password = $("#modalPassword").val();
	
	$("#modalPassword").val("");
	
	var modalGuestbookVo = {
			no: no,
			password: password
	}

	$.ajax({
		url : "${pageContext.request.contextPath }/gb/api/delete",
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(modalGuestbookVo),
		
		dataType : "json",
		success : function(no){
			/*성공시 처리해야될 코드 작성*/
			$("#del-pop").modal("hide");
			
			$("[id="+no+"]").remove();
		},
		
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
});

// <a href=''>삭제</a> ${pageContext.request.contextPath }/gb/api/deleteform?no="+guestbookVo.no+"

// <button type="button" class="btn btn-danger" id="btn_del">삭제</button>
function render(guestbookVo , updown){
	var str = "";
	str += "<li id="+guestbookVo.no+">";
	str += "	<table>";
	str += "	   <tr>";
	str += "	   	   <td>[" +guestbookVo.no+"]</td>";
	str += "	   	   <td>"+guestbookVo.name+"</td>";
	str += "	   	   <td>"+guestbookVo.reg_date+"</td>";
	str += "	   	   <td><button class='btn_del_content' type='button' data-no="+guestbookVo.no+">삭제</button></td>";
	str += "	   </tr>";
	str += "	   <tr>";
	str += "	   	   <td colspan=4>"+guestbookVo.content+"</td>";
	str += "	   </tr>";
	str += "	</table>";
	str += "<br>";
	str += "</li>";
	
	if(updown == "up"){
		$("#listArea").prepend(str);
	}else if(updown == "down"){
		$("#listArea").append(str);
	}else{
		console.log("updown 오류");
	}
	
	
};
</script>


</html>

