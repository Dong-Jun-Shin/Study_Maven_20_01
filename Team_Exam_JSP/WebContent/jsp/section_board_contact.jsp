<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="stylesheet" type="text/css" href="../css/board.css">
<link rel="stylesheet" type="text/css" href="../css/contact.css">

<script type="text/javascript" src="../js/board_contact.js"></script>

<div class="container">
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
					<th class="title">제목</th>
					<th class="user">글쓴이</th>
					<th class="date">등록일</th>
					<th class="response">답변상태</th>
				</tr>
			</thead>
			<tbody id="board_list">
				<!-- 목록이 추력될 곳 -->
				<tr id="contact-template" class="contact-list">
					<td class="no"></td>
					<td class="title"></td>
					<td class="user"></td>
					<td class="date"></td>
					<td class="response"></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>