$(document).ready(function() {
    const modifyModal = $(".review_modify_modal");

    $(".review_modify_btn").click(function() {
        const orderNum = $(this).data("orderNum");
        const storeId = $(this).data("storeId");
        const score = Number($(this).data("score"));
        const content = $(this).data("content");
        const reviewImg = $(this).data("img");

        modifyModal.find(".order_num").val(orderNum);
        modifyModal.find(".store_id").val(storeId);
        modifyModal.find("textarea").val(content);
        modifyModal.find(".score").val(score);

        updateScoreStars(modifyModal, score);
        toggleSubmit(modifyModal);

        if (reviewImg) {
            modifyModal.find(".preview").attr("src", reviewImg);
            modifyModal.find(".img_box div").css("display", "block");
        } else {
            modifyModal.find(".preview").attr("src", "");
            modifyModal.find(".img_box div").css("display", "none");
        }

        modifyModal.find(".score_box i").off().click(function() {
            const starIndex = $(this).index() + 1;
            modifyModal.find(".score").val(starIndex);
            updateScoreStars(modifyModal, starIndex);
            toggleSubmit(modifyModal);
        });

        modifyModal.find(".review_text textarea").off().keyup(function() {
            toggleSubmit(modifyModal);
        });

        openModal(modifyModal);
    });

    $(".review_delete_btn").click(function() {
        const orderNum = $(this).data("orderNum");

        swal({
            title: "리뷰를 삭제할까요?",
            text: "삭제한 리뷰는 복구할 수 없습니다.",
            buttons: ["취소", "삭제"],
            dangerMode: true
        }).then(function (value) {
            if (!value) return;

            $.ajax({
                url: "/store/reviewDelete",
                type: "POST",
                data: { orderNum: orderNum }
            }).done(function() {
                location.reload();
            }).fail(function(xhr) {
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

    $(".img").change(function(e){
        imgPreview(e, $(this));
        toggleSubmit(modifyModal);
    });

    $(".img_close").click(function(){
        imgClose();
        toggleSubmit(modifyModal);
    });
});

function updateScoreStars(modal, score) {
    modal.find(".score_box i").removeClass("fas").addClass("far");
    if(score > 0) {
        modal.find(".score_box i").each(function(index) {
            if(index < score) {
                $(this).removeClass("far").addClass("fas");
            }
        });
    }
}

function toggleSubmit(modal) {
    const text = modal.find(".review_text textarea").val();
    const score = modal.find(".score").val();

    if (text.trim().length === 0 || !score) {
        modal.find(".review_submit_btn")
             .attr("disabled", true)
             .css("background", "#ddd");
    } else {
        modal.find(".review_submit_btn")
             .attr("disabled", false)
             .css("background", "#30DAD9");
    }
}

