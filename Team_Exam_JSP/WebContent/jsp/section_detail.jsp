<%@page import="team.board.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="team.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%
	BoardDAO bdao = BoardDAO.getInstance();
	ArrayList<BoardVO> list = bdao.getSelBoard(request);
	int counter = list.size();
	BoardVO bvo = new BoardVO();
	if(counter > 0){
		bvo = list.get(0);
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />

		<title>상세 페이지</title>
		
      	
		<link rel="shortcut icon" href="../image/icon.png" />
		<link rel="apple-touch-icon" href="../image/icon.png" />

		<style  type = "text/css">
			table{
				margin:
			}
			#b_writer{
				text-align:left;
			}
			#b_file{
				text-align:right;
			}
			h2{
				border-bottom: 2px dashed  #00bd39;
			}
			
		</style>
		
		<script type = "text/javascript" src = "../js/jquery-1.12.4.min.js"></script>
	</head>
	<body>
		<form>	
			<div>
				<h2 id = "b_title">| <%=bvo.getB_title() %> |</h2>
				<table>
					<tr >
						<td id = "b_writer"><%=bvo.getB_writer() %></td>
						<td id = "b_file">
						<%
							if(bvo.getB_file()==null){
								out.print("파일없음");
							}else{
								out.print(bvo.getB_file()); 
							}
						%>
						</td>
					</tr>
					<tr>
						<td  colspan = "2" id = "b_content"><%=bvo.getB_content() %></td>
					</tr>	
				</table>
				<label>댓글</label>
				<label>조회수<%=bvo.getB_hits() %> </label>
				<div>
					<input type="text" id = "comment" name = "comment">
					<input type = "button" id = "btn" name = "btn" value = "등록" />
				</div>
			</div>
		</form>
	</body>
</html>