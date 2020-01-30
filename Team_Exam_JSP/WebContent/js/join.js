function formValid() {
	var form = document.infoForm;

	// 이름 검증
	if (form.in_name.value.replace(/\s/g, "") == "") {
		alert("이름을 입력해주세요.");
		form.in_name.value = "";
		form.in_name.focus();
		return;
	}

	// 전화번호 검증
	if (form.in_phone.value.replace(/\s/g, "") == "") {
		alert("전화번호를 입력해주세요.");
		form.in_phone.value = "";
		form.in_phone.focus();
		return;
	}

	// 이메일 검증
	if (form.in_email.value.replace(/\s/g, "") == "") {
		alert("이메일을 입력해주세요.");
		form.in_email.value = "";
		form.in_email.focus();
		return;
	}

	// 비밀번호 검증
	if (form.in_pw.value.replace(/\s/g, "") == "") {
		alert("이메일을 입력해주세요.");
		form.in_pw.value = "";
		form.in_pw.focus();
		return;
	}

	var img = $("<img>").attr("src", "../image/join_complete.png").attr(
			"width", "650px");
	$("#section1 > div.container").html(img);
	form.reset();
}