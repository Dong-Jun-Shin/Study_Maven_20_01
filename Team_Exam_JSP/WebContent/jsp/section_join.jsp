<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" type="text/css" href="../css/join.css">

<script type="text/javascript" src="../js/join.js"></script>

<div class="container">
	<form action="" method="get" name="infoForm">
		<fieldset class="shadow frame ">
			<legend>정보 기입</legend>
			<div class="box">
				<span> <label><strong>*</strong>이름 : </label> <input
					type="text" name="in_name" maxlength="10">
				</span>
			</div>
			<div class="box">
				<span> <label><strong>*</strong>전화번호 : </label> <input
					type="tel" name="in_phone" maxlength="13">
				</span>
			</div>
			<div class="box">
				<span> <label><strong>*</strong>이메일 : </label> <input
					type="email" name="in_email" maxlength="50">
				</span>
			</div>
			<div class="box">
				<span> <label><strong>*</strong>비밀번호 : </label> <input
					type="password" name="in_pw" maxlength="20">
				</span>
			</div>
			<input type="button" value="회원가입" onclick="formValid()">
		</fieldset>
	</form>
</div>