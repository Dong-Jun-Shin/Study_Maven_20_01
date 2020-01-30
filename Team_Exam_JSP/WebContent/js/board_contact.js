function template(no, title, user, date, response){
			var $tbody = $('div.example > table > tbody#board_list');
			
			var $element = $('#contact-template').clone().removeAttr('id');
			$element.find(".no").attr("data-num", no).html(no);;
			$element.find('.title').html(title);
			$element.find('.user').html(user);
			$element.find('.date').html(date);
			$element.find('.response').html(response);
			
			$tbody.append($element);
		}
		
		$(function(){

			$.get("../json/contact.json", {}, function(json){
				$("h1.title").html(json.name);
				$("div.exec:eq(0) > span").html(json.total);
			
				var item = json.item;
				
				for (var i = 0; i < item.length; i++){
					template(item[i].no, item[i].title, item[i].user, item[i].date, item[i].response);
				}
			});
		});	