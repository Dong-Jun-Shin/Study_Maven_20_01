$(function(){
	/* 배너 영역, 광고 영역 slide */
	$("#slides1").slidesjs({
		width: 1040,
		height: 300,
		play: {
			active : true,
			auto : true,
			interval : 2500,
			swap : true,
			effect : "fade"
		}
	});
	
	$("#slides2").slidesjs({
		width: 5,
		height: 6,
		play: {
			active : false,
			auto : true,
			interval : 7000,
			effect : "slide"
		},
		navigation : {
			/* 버튼의 표시 여부 설정 */
			active : false,
			/* 버튼 클릭시 적용될 효과 */
		},
		/* (pagination)현재 위치에 대한 표시 여부와 클릭했을 경우의 효과 제어 */
		pagination : {
			/* 버튼의 표시 여부 설정 */
			active : false,
			/* 버튼 클릭시 적용될 효과 */
		}
	});
	
	/* 알림판 영역, tab */
	$.get("../json/notice_board.json", function(json){
		var notify = json.notice.notify;
		
		var li = $("<li>");
		$(notify).each(function(){
			var title = this.title;
			var date = this.date;
			
			addList(title, date, li);
			$("ul.panel").html(li);
		});
	});
	
	// 탭 클릭시, 콘텐츠 로드
	$("ul.tab li a").click(function(){
		$("ul.tab li a").not(this).removeClass("selected");
		$(this).addClass("selected");

		var index = $(this).attr("href");
		
		$.get("../json/notice_board.json", function(json){
			var list = [json.notice.notify, json.notice.employ, json.notice.movements, json.notice.report];
			
			console.log(list);
			console.log(index);
			
			var hunk = list[index];
			
			var li = $("<li>");
			$(hunk).each(function(){
				var title = this.title; 
				var date = this.date;
				
				addList(title, date, li);
				$("ul.panel").html(li);
			});
		});
		
		return false;
	});
	
	/* 일정 영역, */
	$.get("../json/schedule.json", function(json){
		$("div#schedule strong:nth-child(1)").html(json.schedule.title);
		
		var item = json.schedule.item;
		var div = $("div#schedule");
		
		$(item).each(function(){
			var category = this.category;
			var content = this.content;
			addSchedule(category, content, div);
		});
	});
	
	$("span#date").html(getDate());

	/* 행사 영역, */
	$.get("../json/culture.json", function(json){
			var div = $("div#culture > div:nth-child(2)");
	
			addCulture(json, div);
	});
});

function addCulture(json, div){
	//span title
	var title = $("<strong>").html(json.culture.title);
	div.children().eq(0).html(title);
	
	//ul 
	var li = div.children().eq(1).children(); 
	var element = [json.culture.host, json.culture.condition, json.culture.date, json.culture.price, json.culture.info];
	for (var i = 0; i < element.length; i++) {
		li.eq(i).append(element[i]);
	}				
	
}

function addList(title, date, li){
	var div = $("<div>");
	var h2 = $("<h2>").attr("class", "head");
	var p = $("<p>").attr("class", "content");
	
	h2.html(title);
	p.html(date);
	div.append(h2).append(p)
	li.append(div);
};

function addSchedule(category, content, div){
	var a = $("<a>");
	var strong = $("<strong>").attr("class", "category");
	var span = $("<span>");
	
	strong.html(category);
	span.html(content);
	a.append(strong).append(span);
	div.append(a);
};

function getDate(){
	var mydate = new Date();
	
	var yy = mydate.getFullYear();
	var mm = mydate.getMonth() + 1;
	var dd = mydate.getDate();
	
	var days = ["일", "월", "화", "수", "목", "금", "토"];
	var i = mydate.getDay();
	var day = days[i];
	
	var result = yy + "년 " + mm + "월 " + dd + "일 (" + day + ")";
	
	return result;
};