
$(document).ready(function() {

	// 리뷰 쓰기 버튼
	$(".review").click(function() {
		let modal;

		if ($(this).hasClass("regi")) {
			modal = $(".review_modal");
		} else {
			modal = $(".review_modify_modal");
			
			const reviewContent = $(this).siblings(".review_content").val();
			const reviewScore = $(this).siblings(".review_score").val();
			const reviewImg = $(this).siblings(".review_img").val();
			$(".review_modify_modal textarea").val(reviewContent);
			$(".review_modify_modal .preview").attr("src", reviewImg);
			$(".review_modify_modal .review_img_hidden").val(reviewImg);
			
			// 기존 별점 설정
			if(reviewScore && reviewScore > 0) {
				$(".review_modify_modal .score_box i").removeClass("fas");
				$(".review_modify_modal .score_box i").eq(reviewScore - 1).addClass("fas").prevAll().addClass("fas");
				$(".review_modify_modal .score").val(reviewScore);
			}
			
			if(reviewImg != "" && reviewImg != null) {
				$(".review_modify_modal .img_box div").css("display", "block");
			}
			
		}

		openModal(modal);

		const orderNum = $(this).siblings(".order_num").val();
		const storeId = $(this).siblings(".store_id").val();

		modal.find(".order_num").val(orderNum);
		modal.find(".store_id").val(storeId);
		
		
		
		// 별점주기
		let score = modal.find(".score").val() || 0;
	
		modal.find(".score_box i").off().click(function() {
			score = $(this).index() + 1;
				
			modal.find(".score_box i").removeClass("fas");
			$(this).addClass("fas").prevAll().addClass("fas");
	
			modal.find(".score").val(score);
	
			inputCheck(modal);
		});
		
		
		
		modal.find(".review_text textarea").off().keyup(function() {
			inputCheck(modal);
		})
		
		
		
		// 리뷰 작성, 별점 체크 했는지 확인
		function inputCheck(modal) {
			let text = modal.find(".review_text textarea").val();
			let score = modal.find(".score").val();
			
			if(text.length == 0 || score == "" || score == null || score == 0) {
				modal.find(".review_submit_btn").css("background", "#ddd");
				modal.find(".review_submit_btn").attr("disabled", true);
			} else {
				modal.find(".review_submit_btn").attr("disabled", false);
				modal.find(".review_submit_btn").css("background", "#30DAD9");
			}
		}
		
		// 모달 열 때 초기 상태 체크
		inputCheck(modal);
	});
	




	$(".img").change(function(e){
		imgPreview(e, $(this));
	})
	
	$(".img_close").click(function(){
		imgClose();
	})



	$(".order_detail").click(function() {
		const orderNum = $(this).siblings(".order_num").val();
		location.href = "/orderListDetail/" + orderNum;
	});

	$(".review_delete").click(function() {
		const orderNum = $(this).siblings(".order_num").val();

		swal({
			title: "리뷰를 삭제할까요?",
			text: "삭제 후에는 복구할 수 없습니다.",
			buttons: ["취소", "삭제"],
			dangerMode: true
		}).then(function(isDelete) {
			if (!isDelete) {
				return;
			}

			$.ajax({
				url: "/store/reviewDelete",
				type: "POST",
				data: { orderNum: orderNum }
			})
			.done(function() {
				location.reload();
			})
			.fail(function(xhr) {
				if (xhr.status === 401) {
					swal("로그인이 필요합니다.");
				} else if (xhr.status === 403) {
					swal("삭제할 수 있는 리뷰가 없습니다.");
				} else {
					swal("리뷰 삭제 중 오류가 발생했습니다.");
				}
			});
		});
	});

}); // ready