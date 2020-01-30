<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="../css/board_suggestion.css">

<script type="text/javascript" src="../js/board_suggestion.js"></script>

<div class="container">
	<!-- 게시판 제목이 출력될 곳 -->
	<h1 class="title"></h1>

	<!-- 게시물 수 출력될 곳 -->
	<div class="exec">
		게시판: <span></span>건
	</div>
	<div class="example">
		<table>
			<thead>
				<tr>
					<th class="no">번호</th>
					<th class="best"></th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="date">작성일시</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			<tbody id="board_list">
				<!-- 목록이 추력될 곳 -->
				<tr id="suggestion-template" class="list">
					<td class="no"></td>
					<td class="best"></td>
					<td class="left subject"></td>
					<td class="writer"></td>
					<td class="date"></td>
					<td class="hit"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="plus">
		<input type="button" id="more" value="더보기" />
	</div>
</div>