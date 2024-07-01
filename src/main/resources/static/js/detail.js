document.addEventListener("DOMContentLoaded", function() {
    const jjimButton = document.getElementById("jjim");
    const movieSeq = document.getElementById("movieSeq").value;

    jjimButton.addEventListener("click", function() {
        let liked = jjimButton.getAttribute("data-liked");

        if(jjimButton.src.value=="/image/fullheart.jpg"){
            liked === "true"
        } else {
            liked ==="false"
        }

        const url = "/movie/insertWish";

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ movieSeq: movieSeq })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                jjimButton.src = liked ? "/image/heart.jpg" : "/image/fullheart.jpg";
                jjimButton.setAttribute("data-liked", !liked);
                alert("찜 목록이 수정되었습니다.");
                location.reload();
            } else {
                alert(data.errorMessage || "오류가 발생했습니다. 다시 시도해주세요.");
            }
        })
        .catch(error => console.error("Error:", error));
    });

    window.submitReview = function() {
            const reviewText = document.getElementById("review").value;
            const reviewStar = document.querySelector('input[name="reviewStar"]:checked').value;

            fetch("/movie/submitReview", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    movieSeq: movieSeq,
                    reviewText: reviewText,
                    reviewStar: reviewStar
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 성공적으로 제출되었음을 알리고, 리뷰를 화면에 표시
                    document.getElementById("review").value = ""; // 입력 필드 초기화
                    document.querySelector('input[name="reviewStar"]:checked').checked = false; // 별점 초기화
                    alert("리뷰가 등록되었습니다.");
                    location.reload();
                } else {
                  alert(data.errorMessage || "오류가 발생했습니다. 다시 시도해주세요.");
               }
            })
            .catch(error => console.error("Error:", error));
        };

});






