document.addEventListener("DOMContentLoaded", function() {

    // 첫 번째 체크박스를 선택하면 전체 체크박스를 선택하거나 해제하는 함수
    document.getElementById('selectAll').addEventListener('change', function() {
        let checkboxes = document.querySelectorAll('.memberCheckbox');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = this.checked;
        }, this);
    });

    document.getElementById("deleteButton2").addEventListener("click", async function() {
            const checkboxes = document.querySelectorAll("input[name='checkUser']:checked");
            const userSeqList = Array.from(checkboxes).map(checkbox => checkbox.value);

            if (userSeqList.length > 0) {
                const confirmed = confirm("선택한 게시글을 삭제하시겠습니까?");
                if (confirmed) {
                    try {
                        await deleteUsersRequire(userSeqList);
                        alert("글이 삭제되었습니다.");
                        location.reload();
                    } catch (error) {
                        alert("글 삭제 중 오류가 발생했습니다.");
                        console.error(error);
                    }
                }
            } else {
                alert("삭제할 사용자를 선택해주세요.");
            }
        });

        async function deleteUsersRequire(userSeqList) {
            const response = await fetch('/admin/info/delete', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userSeqList)
            });

            if (!response.ok) {
                throw new Error('사용자 삭제 요청이 실패했습니다.');
            }
        }

});


