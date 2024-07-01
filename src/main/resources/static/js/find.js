document.addEventListener('DOMContentLoaded', function () {

    function findId(){
        let name = document.getElementById("find_name").value;
        let phone = document.getElementById("find_phone").value;
        if (!name) {
            alert("이름을 입력해주세요.");
            return false;
        } else if(!phone){
            alert("전화번호를 입력해주세요.");
            return false;
        }
        return true;

    }

    function findPassword() {
        let id = document.getElementById("find_id").value;
        let name = document.getElementById("find_name2").value;
        let phone = document.getElementById("find_phone2").value;

        if (!id) {
            alert("ID를 입력해주세요.");
            return false;
        } else if (!name) {
             alert("이름을 입력해주세요.");
             return false;
        } else if (!phone) {
             alert("전화번호를 입력해주세요.");
             return false;
        }
        return true;

    }

    document.getElementById("form_id").addEventListener("submit", function (event) {
        if (!findId()) {
            event.preventDefault(); // 폼 제출 중단
        }
    });
    document.getElementById("form_pw").addEventListener("submit", function (event) {
        if (!findPassword()) {
            event.preventDefault(); // 폼 제출 중단
        }
    });

});
