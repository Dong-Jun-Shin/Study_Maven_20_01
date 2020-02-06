<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
		
		<title>Insert title here</title>
		
		<!-- 모바일 웹 페이지 설정 - 이미지 경로 위치는 각자 변경 -->
		<link rel="shortcut icon" href="../image/icon.png" />
		<link rel="apple-touch-icon" href="../image/icon.png" />
		<!-- 모바일 웹 페이지 설정 끝 -->
		<style type="text/css">
			table, tr, td{
				padding:20px;
				align-content: center;
				margin: auto;
				border-collapse: collapse;
/* 				border: 1px solid #00bd39; */
			}
			
			th{
				text-align: right;
				padding:10px;
			}
			
			#submit{
				border-radius: 30px;
				color: white;
				background: #00bd39;
				margin-bottom: 30px;
				margin-top: 15px;
				cursor: pointer;
				border: 2px solid #00bd39;
				font-weight: bold;
				padding: 5px 30px;
				line-height: 30px;
				font-size: 15px;
			}
			
			.send{
				text-align: center;			
			}
			.line1{
				border-bottom: 5px solid #00bd39; 
			}
			.line2{
				border-bottom: 0.5px solid #00bd39;
			}
			#contentLabel{
				text-align: right;
			}
		</style>
		
		<script type="text/javascript" src="../js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="../js/common.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#send").click(function(){
					if(checkExp($("#b_writer"), "글쓴이")) return;
					if(checkExp($("#b_title"), "제목")) return;
					if(checkExp($("#b_content"), "내용")) return;
					
					$("#board_form").attr({
						"method": "post",
						"action": "insertBoard.jsp"
					});
					
					$("#board_form").submit();
				});
			})
		</script>
	</head>
	<body>
		<form name="board_form" id="board_form">
			<table>
				<tr class="line1">
					<th>글쓴이:</th>
					<td><input type="text" name="b_writer" id="b_writer" placeholder="글쓴이를 입력해 주세요"/></td>
				</tr>
				<tr class="line2">
					<th>제목:</th>
					<td><input type="text" name="b_title" id="b_title" placeholder="제목을 입력해 주세요"/></td>
				</tr>
				<tr class="line2">
					<th>첨부파일:</th>
					<td>
						<input type="text" name="b_file" id="b_file"/>
						<input type="button" name="find_file" id="find_file" value="파일 첨부하기"/>
					</td>
				</tr>
				<tr>
					<th id="contentLabel">내용:</th>
					<td rowspan="2">
						<textarea rows="40" cols="100" placeholder="내용을 입력해 주세요" name="b_content" id="b_content"></textarea>
					</td>
				</tr>
			</table>
			<div class="send">
				<input type="button" name="send" id="send" value="입력 완료"/>
			</div>
		</form>
	</body>
</html>