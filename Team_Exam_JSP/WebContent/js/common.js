function checkExp(elem, str){
	var spaceExp = /\s/g;
	
	if(elem.val().replace(spaceExp, "")==""){
		alert(str + "을(를) 입력해주세요.");
		elem.focus();
		elem.val("");
		
		return true;
	}
	
	return false;
}