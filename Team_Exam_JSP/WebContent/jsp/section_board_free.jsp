<%@page import="java.util.ArrayList"%>
<%@page import="team.board.BoardDAO"%>
<%@page import="team.board.BoardVO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<%
	BoardDAO bdao = BoardDAO.getInstance();
	ArrayList<BoardVO> list = bdao.getBoardList();
	int counter = list.size();
%>
<link rel="stylesheet" type="text/css" href="../css/board_free.css">

<script type="text/javascript">
	$(function(){
		$.ajax({
			url: "section_board_free.jsp",
			type: "post"
		})
		
		$("#more").click(function(){
			location.href = "template.jsp?page=board_form.jsp";
		})
		
		//검색 부분
		$("#searchBtn").click(function(){
			$.ajax({
				url: "searchBoard.jsp",
				type: "post",
				data: "keyword=" + $("#search").val() + "&sort=" + $("#sort > option:selected").val(),
				dataType: "text",
				
				success: function(data){
					
				},
				
				error: function(xhr, textStatus, errorThrown){
					
				}
			})
		})
	})
</script>
<div class="container">
	<!-- 게시판 제목이 출력될 곳 -->
	<h1 class="title"></h1>

	<!-- 게시물 수 출력될 곳 -->
	<div class="exec">
		게시판: <span><%=counter %></span>건
	</div>
	
	<div class="example">
		<table>
			<thead>
				<tr>
					<th class="no">번호</th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="date">작성일시</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			<tbody id="board_list">
				<!-- 목록이 출력될 곳 -->
				<%
					if(counter > 0){
						for(BoardVO bvo : list){ 
				%>
							<tr class="list">
								<td class="no"><%=bvo.getB_num() %></td>
								<td class="left subject"><a href="template.jsp?page=detail.jsp&b_num=<%=bvo.getB_num() %>"><%=bvo.getB_title() %></a></td>
								<td class="writer"><%=bvo.getB_writer() %></td>
								<td class="date"><%=bvo.getB_reg_date().subSequence(0, 10) %></td>
								<td class="hit"><%=bvo.getB_hits() %></td>
							</tr>
				<%
						} 
					}
				%>
			</tbody>
		</table>
	</div>
	<div class="search">
		<label>검색조건</label>
		<select name="sort" id="sort">
			<option>전체</option>
			<option value="b_title">제목</option>
			<option value="b_writer">글쓴이</option>
		</select>
		<input type="text" placeholder="검색어를 입력하세요." name="search" id="search" />
		<button type="button" name="searchBtn" id="searchBtn">검색</button>
	</div>
	<div class="plus">
		<input type="button" id="more" value="등록하기" />
	</div>
</div>