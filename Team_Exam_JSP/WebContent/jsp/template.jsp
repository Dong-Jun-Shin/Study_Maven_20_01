<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = "/jsp/section_";
	String pagefile = request.getParameter("page");
	if(pagefile==null){
		pagefile="index.jsp";
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/jsp/top.jsp" />
	</head>
	<body>
		<!-- header ------------------------------ -->
		<header>
			<jsp:include page="/jsp/header.jsp"/>
		</header>
		
		<!-- nav --------------------------------- -->
		<nav>
			<jsp:include page="/jsp/navigator.jsp" />
		</nav>
		
		<!-- section ---------------------------- -->
		<section id="section">
			<jsp:include page="<%=path + pagefile %>" />
		</section>
		
		<!-- footer -------------------------- -->
		<footer class="left">
			<jsp:include page="/jsp/footer.jsp"/>
		</footer>
	</body>
</html>