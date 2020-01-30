function initMap(){
	// 위치 좌표
	var location = {lat: -6.179363, lng: 35.740788};
	
	// 맵 생성
	var map = new google.maps.Map(document.getElementById('map'), {
		center: location,
		scrollwheel: true,
		zoom: 17
	});
	
	// 정보 문구 생성
	var contentString = "<h2>Majengo</h2>";
	contentString += "<p>예술의 전당: 탄자니아 본점<br /> Ndovu Rd, Dodoma, 탄자니아</p>";
	var infowindow = new google.maps.InfoWindow({
		content: contentString
	});
	
	// 마커 생성
	var marker = new google.maps.Marker({
		map: map,
		position: location,
		title: "Majengo, Tanzania"
	});
	
	// 마커 이벤트 부여
	marker.addListener('mouseover', function(){
		infowindow.open(map, marker);
	});
	
	marker.addListener('mouseout', function(){
		infowindow.close();
	});
}