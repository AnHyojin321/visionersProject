function checkPasswordMatch() {
        var password = document.getElementById('userpassword').value;
        var confirmPassword = document.getElementById('confirmPassword').value;
        var statusText = document.getElementById('passwordMatch');
        if (password === confirmPassword) {
            statusText.innerText = "비밀번호가 일치합니다";
            statusText.style.color = 'green';
        } else {
            statusText.innerText = "비밀번호가 일치하지 않습니다";
            statusText.style.color = 'red';
        }
    }