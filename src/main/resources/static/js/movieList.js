document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("deleteButton").addEventListener("click", async function() {
        const checkboxes = document.querySelectorAll("input[name='movieCheckbox']:checked");
        const movieSeqList = Array.from(checkboxes).map(checkbox => checkbox.value);

        if (movieSeqList.length > 0) {
            const confirmed = confirm("선택한 영화를 삭제하시겠습니까?");
            if (confirmed) {
                try {
                    await deleteMovie(movieSeqList);
                    alert("영화가 삭제되었습니다.");
                    location.reload();
                } catch (error) {
                    alert("영화 삭제 중 오류가 발생했습니다.");
                    console.error(error);
                }
            }
        } else {
            alert("삭제할 영화를 선택해주세요.");
        }
    });

    async function deleteMovie(movieSeqList) {
        const response = await fetch('/admin/movieList/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(movieSeqList)
        });

        if (!response.ok) {
            throw new Error('영화 삭제 요청이 실패했습니다.');
        }
    }

    // 첫 번째 체크박스를 선택하면 전체 체크박스를 선택하거나 해제하는 함수
    document.getElementById('selectAll').addEventListener('change', function() {
        let checkboxes = document.querySelectorAll('.movieCheckbox');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = this.checked;
        }, this);
    });
});

$(document).ready(function() {
    $("#searchBtn").on("click", function(e) {
      e.preventDefault();
      page(1);
    })
});

function page(page) {
    let text = $("input[name='searchText']").val()
    if(text == null || text == ""){
        location.href="/admin/movieList/" + page ;
    }else{
        location.href="/admin/movieList/" + page + "?searchText="+text;
    }
}
