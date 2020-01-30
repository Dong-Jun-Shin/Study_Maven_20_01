$(function() {
	var video_list = [
		'<iframe id="1" width="1280" height="720" src="https://www.youtube.com/embed/R990HjX3Aqs" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>',
		'<iframe id="2" width="1280" height="720" src="https://www.youtube.com/embed/dOScCnlWPJo" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>',
		'<iframe id="3" width="1280" height="720" src="https://www.youtube.com/embed/EzvSLYtKnnc" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>',
		'<iframe id="4" width="1280" height="720" src="https://www.youtube.com/embed/-bP5iHlIv-I" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>'
	];
		
	//load()함수는 파라미터로 전달받은 특정 경로릐 파일을 읽어들이는 기능을 한다.
	$("div.vido").html(video_list[0]);
	
	 //탭 버튼의 클릭 처리
	$("ul.tab li a").click(function () {
		//클릭된 요서를 제외한 나머지에 pike 클래스 제거-->배경이미지 원상복구 
		$("ul.tab li a").not(this).removeClass("pike");
		
		//틀릭된 요소에게 pike 클래스 적용--> 배경이미지 변경됨
		$(this).addClass("pike");
		
		//클릭된 요소의 href 속성값이 가리키는 파일을 읽어서 <div>출력함
		$("div.vido").html(video_list[$(this).attr("href")-1]); 
		
		//페이지 이동방지
		return false;
	});
});