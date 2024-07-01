document.addEventListener('DOMContentLoaded', function(){

    document.getElementById("update_form").addEventListener("submit", function(event) {
       // 새 비밀번호와 새 비밀번호 확인
        let password = document.getElementById("pw").value;
        let password2 = document.getElementById("pw2").value;
        // 비밀번호와 비밀번호 확인 필드가 일치하지 않으면 알림 메시지를 표시하고 폼 제출을 중단합니다.
        if (password !== password2) {
            alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
            event.preventDefault(); // 폼 제출 중단
            return;
        }

        // 비밀번호가 지정된 조건에 맞는지 정규식으로 검사합니다.
        let passwordRegex = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,16}$/;
        if (!passwordRegex.test(password)) {
            alert("비밀번호는 숫자, 영문자, 특수문자를 포함하여 최소 8자 이상 16자 이하여야 합니다.");
            event.preventDefault(); // 폼 제출 중단
            return;
        }
        alert("비밀번호 변경이 완료됐습니다.");
    });
});