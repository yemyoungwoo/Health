// --------------------- 유효성 검사 ---------------------
function usernameCheck(username) {
    const regUsername = /^[A-Za-z0-9]{4,15}$/;
    return regUsername.test(username);
}

function emailCheck(email) {
    const regEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    return regEmail.test(email);
}

function phoneCheck(phone) {
    // 전화번호에서 하이픈 제거
    const cleanPhone = phone.replace(/-/g, '');
    // 010, 011, 016, 017, 018, 019로 시작하는 10~11자리 숫자
    const regPhone = /^01[0|1|6|7|8|9][0-9]{7,8}$/;
    return regPhone.test(cleanPhone);
}

function nicknameCheck(nickname) {
    const regNickname = /^[가-힣a-zA-Z0-9]+$/;
       if (nickname.length < 4 || nickname.length > 10) {
        return false;
    }
    return regNickname.test(nickname);
}

function lengthCheck(e, length) {
    if (e.value.length >= length) return false;

    $(e).off('focusout').focusout(function () {
        if (e.value.length > length) e.value = "";
    });

    return true;
}

// --------------------- 모달 열기/닫기 ---------------------
function openModal(modal) {
    const size = window.innerWidth;
    if (size > 767) {
        modal.css("transition", "0s").css("top", "0%");
    } else {
        modal.css("transition", "0.2s").css("top", "0%");
    }

    $("#modal_bg").show();
    $("body").css("overflow", "hidden");

    $(".closeA, .closeB").click(closeModal);
    $("#modal_bg").click(closeModal);
}

function closeModal() {
    $("#modal_bg").hide();
    $(".modal").css("top", "100%");
    $(".modal_box").scrollTop(0);
    $("body").css("overflow", "visible");
    $(".modal input[type='checkbox']").prop("checked", false);
    $(".option_box i").removeClass("checked");
    $("#amount").val(1);
}

// --------------------- 문서 준비 ---------------------
$(document).ready(function () {

    // 메뉴 클릭 시 모달 열기
    $('.menu li').click(function () {
        const foodId = $(this).find('.food_id').val();
        const foodName = $(this).find('.food_name').val();
        const foodPrice = $(this).find('.food_price').val();
        const foodDec = $(this).find('.food_dec').val();
        const foodImg = $(this).find('.food_img').val();

        // 모달 정보 설정
        $('.menu_img').attr('src', foodImg);
        $('.menu_name').text(foodName);
        $('.menu_dec').text(foodDec);
        $('.menu_price').text(parseInt(foodPrice).toLocaleString());

        $('.add_cart_food_name').val(foodName);
        $('.add_cart_food_price').val(foodPrice);
        $('.add_cart_food_id').val(foodId);

        loadFoodOptions(foodId);
        openModal($('.gym_modal'));
    });

    // 옵션 가져오기
    function loadFoodOptions(foodId) {
        $.ajax({
            url: '/foodOption',
            type: 'GET',
            data: { foodId: foodId },
            success: function (options) {
                updateOptions(options);
            },
            error: function () {
                console.log('옵션 로드 실패');
            }
        });
    }

    // 수량 조절
    $('.plus').click(function () {
        const amount = parseInt($('#amount').val());
        $('#amount').val(amount + 1);
        calculateTotal();
    });

    $('.minus').click(function () {
        const amount = parseInt($('#amount').val());
        if (amount > 1) {
            $('#amount').val(amount - 1);
            calculateTotal();
        }
    });

    // 옵션 체크박스 클릭 시 색상 변경 및 총액 계산
    $(document).off('click', '.option_box i').on('click', '.option_box i', function(e) {
        e.stopPropagation();
        e.preventDefault();
        const icon = $(this);
        const checkbox = icon.siblings('input[type="checkbox"]').first();
        
        if (checkbox.length) {
            const isChecked = !checkbox.prop('checked');
            checkbox.prop('checked', isChecked);
            
            // 색상 변경 - 인라인 스타일 직접 적용
            if (isChecked) {
                icon.addClass('checked');
                icon.css('color', '#30DAD9');
                // CSS !important를 우회하기 위해 강제로 style 속성에 직접 설정
                icon[0].style.setProperty('color', '#30DAD9', 'important');
            } else {
                icon.removeClass('checked');
                icon.css('color', '#ccc');
                icon[0].style.setProperty('color', '#ccc', 'important');
            }
            
            // 총액 계산
            calculateTotal();
        }
        return false;
    });
    
    // 옵션 로드 후 체크박스 상태에 따라 초기 색상 설정
    function updateOptions(options) {
        const optionList = $('#option ul');
        optionList.empty();

        if (options && options.length > 0) {
            options.forEach(function (option) {
                const optionHtml = `
                    <li>
                        <div class="option_box">
                            <span>
                                <input type="checkbox" class="menu_option" name="option" value="${option.id}">
                                <i class="fas fa-check-square"></i>
                                ${option.optionName}
                                <input type="hidden" class="option_price" value="${option.optionPrice}">
                                <input type="hidden" class="option_id" value="${option.id}">
                            </span>
                            <span>${parseInt(option.optionPrice).toLocaleString()}원</span>
                        </div>
                    </li>
                `;
                optionList.append(optionHtml);
            });
            
            // 모든 체크박스와 아이콘 초기화
            $('.menu_option').prop('checked', false);
            $('.option_box i').each(function() {
                $(this).removeClass('checked');
                this.style.setProperty('color', '#ccc', 'important');
            });
            
            // 옵션 로드 후 체크박스 이벤트 재등록 (storeDetail.js의 .off() 이후에 다시 등록)
            setTimeout(function() {
                $(document).off('click', '.option_box i').on('click', '.option_box i', function(e) {
                    e.stopPropagation();
                    e.preventDefault();
                    const icon = $(this);
                    const checkbox = icon.siblings('input[type="checkbox"]').first();
                    
                    if (checkbox.length) {
                        const isChecked = !checkbox.prop('checked');
                        checkbox.prop('checked', isChecked);
                        
                        // 색상 변경 - 인라인 스타일 직접 적용
                        if (isChecked) {
                            icon.addClass('checked');
                            icon[0].style.setProperty('color', '#30DAD9', 'important');
                        } else {
                            icon.removeClass('checked');
                            icon[0].style.setProperty('color', '#ccc', 'important');
                        }
                        
                        // 총액 계산
                        calculateTotal();
                    }
                    return false;
                });
            }, 100);
        } else {
            optionList.append('<li>선택 가능한 옵션이 없습니다.</li>');
        }
    }

    // 총액 계산
    function calculateTotal() {
        const basePrice = parseInt($('.add_cart_food_price').val()) || 0;
        const amount = parseInt($('#amount').val()) || 1;
        let optionPrice = 0;

        $('.menu_option:checked').each(function () {
            optionPrice += parseInt($(this).siblings('.option_price').val()) || 0;
        });

        const total = (basePrice + optionPrice) * amount;
        $('.total_price').text(total.toLocaleString());
    }

     //장바구니 담기 - storeDetail.js에서 처리하므로 주석 처리
     $('.add_cart').click(function () {
         alert('장바구니에 담았습니다!');
         closeModal();
     });
});

function imgPreview(e,target){
	const previewBox = target.siblings("div");
	const preview = previewBox.find(".preview");
	const fileReader = new FileReader();

	fileReader.readAsDataURL(e.target.files[0]);

	fileReader.onload = function() {
		preview.attr("src", fileReader.result);
		previewBox.css("display", "block");
	}
}
	
	
function imgClose() {
	$(".preview").attr("src", "");
	$(".img").val("");
	$(".img_box div").css("display", "none");
}

const timer = (function(){
	let time;	// 타이머 시간
	let timerStart;	// setInterval 이름
	let timerArr = [];	// 실행중인 타이머
	let isStart = true;	// 재시작 가능여부 
	let restartTime;	// 재시작 가능한 시간
	
	let minute;
	let second;
	
	const start = function(){
		if(!isStart) {
			return false;
		}
		
		// 타이머 초기화하기
		const reset = function(){
			time = 300;
			restartTime = 30;
			
			minute = Math.floor(time / 60);
			second = time % 60;
		}
		
		// 배열이 사이즈가 0이면 처음 실행
		if(timerArr.length == 0) {
			reset();
		}
		
		// 배열이 사이즈가 1이면 재실행 
		if(timerArr.length == 1) {
			// 재실행시 실행중인 타이머를 종료하고 다시 실행
			clearInterval(timerArr.pop());
			reset();
		}
		
		isStart = false;
			
		$(".timer").text(minute + ' : ' + String(second).padStart(2,'0'));
		
		timerStart = setInterval(function(){
			if(0 < restartTime) {
				restartTime--;
			}
			
			if(restartTime == 0) {
				isStart = true;
			}
			
			if(second == 0) {
				minute--;
				if(minute == -1) {
					for(i=0;i<timerArr.length;i++) {
						clearInterval(timerArr[i]);
					}
					timerArr = [];
					return;
				}
				second = 60;
			}
			second--; 
			$(".timer").text(minute + ' : ' + String(second).padStart(2,'0'));
			
		}, 1000)
		timerArr.push(timerStart);
	}
	
	const status = function(){
		return {
			isStart : isStart,
			restartTime : restartTime,
		}
	}
		
	return {
		start : start,
		status : status,
	}
})();
