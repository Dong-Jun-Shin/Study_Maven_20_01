<%@page import="team.board.BoardVO"%>
<%@page import="team.board.BoardDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String b_writer = request.getParameter("b_writer");
	String b_title = request.getParameter("b_title");
	String b_content = request.getParameter("b_content");
	String b_file = request.getParameter("b_file");
%>
<jsp:useBean id="bvo" class="team.board.BoardVO"></jsp:useBean>
<jsp:setProperty property="*" name="bvo" />
<%
	BoardDAO bdao = BoardDAO.getInstance();
	int result = bdao.boardInsert(bvo);
	if(result==1){
%>
	<script type="text/javascript">
		alert("등록에 성공했습니다.");
		location.href="template.jsp?page=board_free.jsp";
	</script>
<%	}else{%>
	<script type="text/javascript">
		alert("등록에 실패했습니다.");
		location.href="template.jsp?page=board_free.jsp";
	</script>
<%	} %>					