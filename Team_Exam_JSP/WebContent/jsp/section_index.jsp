<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="stylesheet" type="text/css" href="../css/index.css">
<link rel="stylesheet" type="text/css" href="../css/navigation.css">

<script type="text/javascript" src="../plugins/slidesJS/jquery.slides.min.js"></script>
<script type="text/javascript" src="../js/index.js"></script>

<div class="container">
	<div class="main">
		<div id="slides1">
			<img src="../image/banner1.gif" />
			<img src="../image/banner2.gif" />
			<img src="../image/banner3.gif" />
		</div>
	</div>
	<div class="margin_bot large_notice box left">
		<ul class="tab">
			<li><a href="0" class="selected">공지사항</a></li>
			<li><a href="1">채용정보</a></li>
			<li><a href="2">박물관 동정</a></li>
			<li><a href="3">보도자료</a></li>
		</ul>
		<ul class="panel shadow">
		</ul>
	</div>
	<div id="panda" class="margin_bot small_addon box right">
		<div id="slides2">
			<img src="../image/addon.gif" alt="도도한 판다"/>
			<img src="../image/addon1.gif" alt="진주 판다"/>
			<img src="../image/addon2.gif" alt="비율 판다"/>
			<img src="../image/addon3.gif" alt="신선 판다"/>
		</div>
		<!-- 광고 추가 -->
	</div>
	<div class="text left">문화 행사</div>
	<div class="margin_top small_schedule box left">
		<div id="schedule" class="shadow">
			<strong></strong><span id="date"></span>
			<img id="add" alt="schedule_more.gif" src="../image/schedule_more.gif" />
		</div>
	</div>
	<div class="margin_top large_culture box right">
		<div id="culture" class="shadow">
			<div>
				<img alt="culture.jpg" src="../image/culture.jpg"/>
			</div>
			<div>
				<span></span>
				<ul>
					<li><strong>주최 : </strong></li>
					<li><strong>제한연령 : </strong></li>
					<li><strong>공연일시 : </strong></li>
					<li><strong>가격 : </strong></li>
					<li><strong>공연안내 : </strong></li>
				</ul>
			</div>
		</div>
	</div>
</div>