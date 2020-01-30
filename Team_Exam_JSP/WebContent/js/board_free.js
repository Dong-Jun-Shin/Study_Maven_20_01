function template(no, subject, writer, hit, date){
				var $tbody = $('div.example > table > tbody#board_list');
				
				var $element = $('#free-template').clone().removeAttr('id');
				$element.find(".no").attr("data-num", no).html(no);;
				$element.find('.subject').html(subject);
				$element.find('.writer').html(writer);
				$element.find('.hit').html(hit);
				$element.find('.date').html(date);
				
				$tbody.append($element);
			}
			
			$(function(){
				//get 방식으로 json 데이터의 요청
				$.get("../json/board_free.json", {}, function(json){
					
					//제목, 내용, 전체 글 수를 화면에 출력
					$("h1.title").html(json.title);
					$("div.exec:eq(0) > span").html(json.total);
					
					var item = json.item;
					
					for (var i = 0; i < item.length; i++){
						template(item[i].no, item[i].subject, item[i].writer, item[i].hit, item[i].date);
					}
				});
				
				
			});	//$ 종료구문