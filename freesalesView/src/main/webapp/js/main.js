function selectedDates() {
	var selecVvalues = [];
	if (!$('#monthsList .selected:not(.disabled)').length) {
		$("#selectedDates").val("");
	}
	$('#monthsList .selected:not(.disabled)').each(function (index, element) {
		selecVvalues.push( $(this).attr("data-item") );
		$("#selectedDates").val( selecVvalues.join(',') );
	})
}

function scrollitem($this) {

    if ($($this[0]).hasClass("calendar-scroll")) {
        $('#monthsList li').each(function (index, element) {
            $(this).find("h2").removeClass("active")
            if ($(this).position().top < $('.calendar-scroll').scrollTop()) {
                if ($(this).position().top + $(this).height() > $('.calendar-scroll').scrollTop()) {
                    $(this).find("h2").css({
                        top: $('.calendar-scroll').scrollTop() - $(this).position().top
                    }).addClass("active")
                }
            }
        });


        if ($('.calendar-scroll').scrollTop() > $('.calendar-scroll .scroll-inner').height() - $('.calendar-scroll').height() - 10) {
            $(".col-mfull .more").show();
            for (var i = 0; i < 2; i++) {

                var monthBits = $('#monthsList li:last').attr("data-date").split('/');
                var month = Number(monthBits[0]) + 1;
                var year = Number(monthBits[1]);


                var newMonth = month % 12;
                if (newMonth == 0) {
                    newMonth = 12
                }
                var NewYearA = (month - newMonth) / 12 + year;
                
    			//agrega el chino para q tenga 2 digitos
                var testCallback = function ($td, thisDate, month, year) {
                	var day2show = Number($td.html());
					if ($td.hasClass("other-month")) {
						if (newMonth-1==0) {
							$td.attr("data-item", +year-1+''+ 12 + '' + (day2show < 10 ? '0' : '') + day2show).html("<span><input type='checkbox'>" + day2show + "</span>");
						} else {
							$td.attr("data-item", +year+''+ (newMonth < 11 ?'0':'') + (newMonth-1) + '' + (day2show < 10 ? '0' : '') + day2show).html("<span><input type='checkbox'>" + day2show + "</span>");
						}
					} else {
						$td.attr("data-item", +year+''+(newMonth < 10 ?'0':'')+newMonth + (day2show < 10 ? '0' : '') + day2show).html("<span><input type='checkbox'>" + day2show + "</span>");
					}
                };
                $('#monthsList').append("<li class='asdasda'></li>")

                $(".asdasda").attr("data-date", (newMonth < 10 ? '0' : '') + newMonth + "/" + NewYearA).renderCalendar({
                    month: (newMonth < 10 ? '0' : '') + newMonth-1,
                    year: NewYearA,
                    renderCallback: testCallback
                }).prepend("<h2>" + Date.monthNames[newMonth - 1] + " " + NewYearA + "</h2>")


                $(".asdasda tr:last-child td").each(function (index, element) {

                    if ($(this).hasClass("other-month")) {
                        $(this).parent().remove()
                    }
                });
                $(".asdasda").removeClass("asdasda")

                window.setTimeout(function () {
                    $("#monthsList table").shiftSelectTable();
                    $(".col-mfull .more").hide();
                }, 500);

            }
        }
    }
}

function resizeCheck() {
    $('.scroll').scrollbarPaper();
    window.setTimeout(function () {
        var totalOtherHeight = 0;
        totalOtherHeight = $('.navbar').outerHeight() + $('.alert').outerHeight()
        $('#body').css({
            top: totalOtherHeight,
            bottom: $('.bottom-notice').outerHeight()
        });
    }, 0);
    var centerEl = ($(window).height() - $(".navbar").height() - $(".new-user").height()) / 2
    $('.new-user').css({
        marginTop: centerEl > 0 ? centerEl : 0
    })
};

/**
 * Se agrego esta funcion
 * Modificado por Gattex
 * @param linkObj
 * @returns {Boolean}
 */

function deselectAll(linkObj) {
	$(linkObj).text("Select All").addClass("select-all").removeClass("deselect-all")
       $(linkObj).parent().find("input").each(function (index, element) {
    	   $(element).attr('checked', false);
    	   $(element).attr('checked', false).next().removeClass("checked")
       });
    return false;
};


/**
 * Se agrego esta funcion.
 * Modificado por Gattex
 */
function resetCalendar(){
    $('#monthsList input').attr("checked", false);
    $('#monthsList input').removeClass("clicked");
    $('#monthsList .selected').removeClass("selected");
    $('.scroll-inner').removeAttr('style');
//    $('.scroll-inner').attr('overflow','hidden');
//    $('.scroll-inner').attr('width','0px');
	selectedDates()
}


/**
 * Fue modificado la funcion, en vez de llamarse cuando el DOM está listo, se lo invoca cuando el modulo de GWT esté ya cargado.
 * Modificado por Gattex
 */
function onReadyMain() {

	$('.details-list .more a').live("click", function () {
		var buttonText = $(this);
		var tableDiv = $(this).parentsUntil("article").parent().find(".table");
		if (tableDiv.is(":visible")) {
			buttonText.text("Details");
			tableDiv.slideUp();
		} else {
			buttonText.text("Hide");
			tableDiv.slideDown();
		}
		return false;
	})

	$('.details-list .add-dates a').live("click", function () {
		$(this).parent().hide().prev(".hidden").slideDown();
		return false;
	})

	$('.details-list .add-row a').live("click", function () {
		$(this).parent().hide().prev(".hidden").slideDown();
		return false;
	})

    window.setTimeout(function () {
        $("#monthsList table").shiftSelectTable();
		
        $('.dates-days .today-link').trigger("click")


    }, 500);
    $(".rooms-check-list input[type=checkbox]").each(function (index) {
        $(this).attr("id", "index-check-" + index).next("label").attr("for", "index-check-" + index);
        if ($(this).is(":checked")) {
            $(this).next("label").addClass("checked");
        } else {
            $(this).next("label").removeClass("checked");
        }
    }).change(function () {
        if (!$(this).is(":checked") == "") {
            $(this).attr('checked', 'checked').next("label").addClass("checked");
        } else {
            $(this).removeAttr('checked', 'checked').next("label").removeClass("checked");
        }
    });

    $(".bottom-notice input[type=radio]").each(function (index) {
        if ($(this).is(":checked")) {
            $(this).next("label").addClass("checked");
        } else {
            $(this).next("label").removeClass("checked");
        }
    })

    $(".bottom-notice input[type=radio]").change(function () {
        $(".bottom-notice .show-hide label").removeClass("checked");
        if (!$(this).is(":checked") == "") {
            $(this).next("label").addClass("checked");
        } else {
            $(this).next("label").removeClass("checked");
        }
    });

    $('.group.wrap>input').change(function () {
		var groupitems = $(this).next().next();
		if (!$(this).is(":checked") == "") {
			$("label:not(.checked)", groupitems).each(function() {
				$(this).prev().trigger("click")
			});
		}
    })

    $('.group.wrap ul input').change(function () {
		var groupihead = $(this).parentsUntil(".group").parent().children("label");
		if ($(this).is(":checked") == "") {
			if ($(groupihead).hasClass("checked")) {
				$(groupihead).prev().trigger("click")
			}
		}
    })


    $('#notifications li').live("click", function () {
        $(this).removeClass("new")
    })
    $('.alert').bind('closed', function () {
        resizeCheck();
    })
    $('.modal').bind('shown', function () {
        resizeCheck();
    })
    resizeCheck();
    $(window).resize(function () {
        resizeCheck();
    });

    if ($('#monthsList').length) {

        var d = new Date();

        var month = d.getMonth() + 1;
        var day = d.getFullYear();
		
		var calendarHeight = ($(window).height()/200).toFixed(0)

        for (var i = 0; i < calendarHeight; i++) {

            $("#monthsList").append("<li>")
        }
        $("#monthsList li").each(function (index) {
            var monthcc = month + index;

            var newMonth = monthcc % 12;
            var NewYearA = (monthcc - newMonth) / 12 + day;
            $(this).html((newMonth < 10 ? '0' : '') + newMonth + "/" + NewYearA).attr("data-date", (newMonth < 10 ? '0' : '') + newMonth + "/" + NewYearA)

        });

        var testCallback = function ($td, thisDate, month, year) {
            $td.html('<input type="checkbox">' + $td.html());
            $('#monthsList li:not(:last-child) tbody tr').last().addClass("asdasd");
        }
        $('#monthsList li').each(function (index) {
            var monthBits = $(this).html().split('/');
            var month = Number(monthBits[0]) - 1;
            var year = Number(monthBits[1]);
			var mene = $(this).attr("data-date");
			
			//agrega el chino para q tenga 2 digitos
			var month2show = Number(mene.split('/')[0]);
			
            var testCallbackaa = function ($td, thisDate, month, year) {
            	var day2show = Number($td.html());
				if ($td.hasClass("other-month")) {
					$td.attr("data-item", +year +''+ (month2show < 11 ?'0':'') + (month2show-1) + '' + (day2show < 10 ? '0' : '') + day2show).html("<span><input type='checkbox'>" + day2show + "</span>");
				} else {
					$td.attr("data-item", +year +''+ (month2show < 10 ?'0':'') + month2show + '' +(day2show < 10 ? '0' : '') + day2show).html("<span><input type='checkbox'>" + day2show + "</span>");
				}
            };

            $(this).renderCalendar({
                month: month,
                year: year,
                renderCallback: testCallbackaa
            });
            $(this).prepend("<h2>" + Date.monthNames[month] + " " + year + "</h2>")

            $("tr:last-child td", this).each(function (index, element) {

                if (!$(this).parent().find(".today").length) {
					if ($(this).hasClass("other-month")) {
						$(this).parent().remove()
					}
                } else {
					$(this).parentsUntil("li").parent().next("li").addClass("movedTO")
				}
            });
			
			$(".movedTO").each(function() {
				if ($("tr:first-child", this).find(".other-month").length) {
					$("tr:first-child", this).remove()
				}
			});

            $(".today", this).each(function (index, element) {
                $(this).parent().prevAll().addClass("disabled-tr")
                $(this).prevAll().addClass("disabled")
            });
        })
        $('.asdasd').remove();


        $('.dates-days .today-link').live("click", function () {
            $('#monthsList h2').removeClass("active")
            $('#monthsList .dp-applied:first-child h2').addClass("active")
            $('.col-mfull .scroll').stop().animate({
                scrollTop: $("#monthsList .today").position().top
            }, 100);
        })

        $('#reset-calendar').live("click", function () {
			resetCalendar();
            return false;
        })

        $('.select-all').live("click", function () {
            $(this).text("Deselect All").addClass("deselect-all").removeClass("select-all")
			$(this).parent().find("input").each(function (index, element) {
                $(this).attr('checked', true).next().addClass("checked")
            });
            return false;
        })

        $('.deselect-all').live("click", function () {
			deselectAll($(this));
            return false;
        })

        $('#monthsList h2').live("click", function () {
            if (!$(this).hasClass("active")) {
                $('#monthsList h2').removeClass("active");
                $(this).addClass("active");
                var scrollToV = $(this).parent("li").position().top + 1
                $('.col-mfull .scroll').animate({
                    scrollTop: scrollToV
                }, 100);
            }
        })
    }
	
	if ($.browser.msie) {
		if ($("html").hasClass("no-backgroundsize")) {
			$("body>div").remove()
			$("body").prepend("<div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 75px; position: fixed; top: 0; right: 0; width: 100%; z-index: 99999'><div style='width: 640px; margin: 0 auto; text-align: left; padding: 0; overflow: hidden; color: black;'>  <div style='width: 75px; float: left;'><img src='http://www.ie6nomore.com/files/theme/ie6nomore-warning.jpg' alt='Warning!'/></div>  <div style='width: 275px; float: left; font-family: Arial, sans-serif;'>    <div style='font-size: 14px; font-weight: bold; margin-top: 12px;'>You are using an outdated browser</div>    <div style='font-size: 12px; margin-top: 6px; line-height: 12px;'>For a better experience using this site, please upgrade to a modern web browser.</div>  </div>  <div style='float: left; line-height: 75px; color: #f00'><a href='http://www.google.com/chrome' target='_blank'>Get Google Chrome</a></div></div></div>");
		}
	}

};