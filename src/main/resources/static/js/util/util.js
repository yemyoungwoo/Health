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
    const regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    return regPhone.test(phone);
}

function nicknameCheck(nickname) {
    const regNickname = /^[가-힣a-zA-Z0-9]+$/;
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
            $('.option_box i').removeClass('checked').css('color', '#ccc');
        } else {
            optionList.append('<li>선택 가능한 옵션이 없습니다.</li>');
        }
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

    // 옵션 체크박스 클릭 시 색상 변경
    $(document).on('click', '.option_box i', function() {
        console.log('아이콘 클릭됨!');
        const checkbox = $(this).siblings('input[type="checkbox"]'); // i 옆 input
        console.log('체크박스 찾음:', checkbox);
        checkbox.prop('checked', !checkbox.prop('checked')).trigger('change');
        console.log('체크 상태:', checkbox.prop('checked'));
    });

    // 옵션 체크박스 변경 시 총액 계산 및 색상 변경
    $(document).on('change', '.menu_option', function () {
        const checkbox = $(this);
        const icon = checkbox.siblings('i');
        
        // 체크박스 상태에 따라 아이콘 색상 변경
        if (checkbox.is(':checked')) {
            icon.addClass('checked');
            icon.css('color', '#333'); // 강제로 색상 변경
        } else {
            icon.removeClass('checked');
            icon.css('color', '#ccc'); // 강제로 색상 변경
        }
        
        calculateTotal();
    });

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

    // 장바구니 담기
    $('.add_cart').click(function () {
        alert('장바구니에 담았습니다!');
        closeModal();
    });
});
