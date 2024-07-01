document.addEventListener('DOMContentLoaded', function () {
    let id = document.getElementById("id");
    let pw = document.getElementById("pw");
    let pw2 = document.getElementById("pw2");
    let email = document.getElementById("email");
    let tel = document.getElementById("tel");
    let pattern_chk1 = /[`~!@#$%^&*|\\\'\";:\/?]/;
    let pattern_chk2 = /[0-9]/;
    let pattern_chk3 = /[a-zA-Z]/;
    let pattern_chk4 = /[~!@#$%^&*()_+|<>?:{}]/;
    let pw_chk2 = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
    let email_chk = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}/;

      window.checkReg = function() {
           if (id.value.length < 5 || id.value.length > 10) {
               alert("아이디는 5~10자리 이어야 합니다.");
               return false;
           } else if (pattern_chk1.test(id.value)) {
               alert("아이디에 특수문자는 들어갈 수 없습니다.");
               return false;
           } else if (pw.value.trim().length == 0) {
               alert("비밀번호를 입력하시기 바랍니다.");
               return false;
           } else if (!pw_chk2.test(pw.value)) {
               alert("비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다.");
               return false;
           } else if(pw.value != pw2.value ){
                alert("비밀번호가 일치하지 않습니다")
                return false;
           } else if (!email_chk.test(email.value)) {
               alert("이메일 형식이 올바르지 않습니다.");
               return false;
           } else if(!pattern_chk2.test(tel.value)){
                alert("전화번호에는 숫자만 입력하세요")
                return false;
           }
           return true;
       }

    // '출생 연도' 셀렉트 박스 option 목록 동적 생성
    const birthYearEl = document.querySelector('#birth-year');
    let isYearOptionExisted = false;
    birthYearEl.addEventListener('focus', function () {
        if (!isYearOptionExisted) {
            isYearOptionExisted = true;
            for (let i = 1940; i <= new Date().getFullYear(); i++) {
                const YearOption = document.createElement('option');
                YearOption.setAttribute('value', i);
                YearOption.innerText = i;
                this.appendChild(YearOption);
            }
        }
    });

    // 월
    const birthMonthEl = document.querySelector('#birth-month');
    let isMonthOptionExisted = false;
    birthMonthEl.addEventListener('focus', function () {
        if (!isMonthOptionExisted) {
            isMonthOptionExisted = true;
            for (let i = 1; i <= 12; i++) {
                const MonthOption = document.createElement('option');
                MonthOption.setAttribute('value', i);
                MonthOption.innerText = i;
                this.appendChild(MonthOption);
            }
        }
    });

    // 일
    const birthDayEl = document.querySelector('#birth-day');
    let isDayOptionExisted = false;
    birthDayEl.addEventListener('focus', function () {
        if (!isDayOptionExisted) {
            isDayOptionExisted = true;
            for (let i = 1; i <= 31; i++) {
                const DayOption = document.createElement('option');
                DayOption.setAttribute('value', i);
                DayOption.innerText = i;
                this.appendChild(DayOption);
            }
        }
    });

    // [year, month, day]
    const birthArr = [-1, -1, -1];
    const birthErrorMsgEl = document.querySelector('#info_birth .fieldError');

    birthYearEl.addEventListener('change', () => {
        birthArr[0] = birthYearEl.value;
        checkBirthValid(birthArr);
    });

    birthMonthEl.addEventListener('change', () => {
        birthArr[1] = birthMonthEl.value;
        checkBirthValid(birthArr);
    });

    birthDayEl.addEventListener('change', () => {
        birthArr[2] = birthDayEl.value;
        checkBirthValid(birthArr);
    });

    function checkBirthValid(birthArr) {
        let isBirthValid = true;
        const y = birthArr[0];
        const m = birthArr[1];
        const d = birthArr[2];
        if (y > 0 && m > 0 && d > 0) {
            if ((m == 4 || m == 6 || m == 9 || m == 11) && d == 31) {
                isBirthValid = false;
            } else if (m == 2) {
                if (((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0)) {
                    if (d > 29) {
                        isBirthValid = false;
                    }
                } else {
                    if (d > 28) {
                        isBirthValid = false;
                    }
                }
            }

            if (isBirthValid) {
                birthErrorMsgEl.textContent = "";
            } else {
                birthErrorMsgEl.textContent = "생년월일을 다시 확인해주세요";
            }
        }
    }
});