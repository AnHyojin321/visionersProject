// URL에 success 파라미터가 있는지 확인
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('success')) {
        // 회원가입 성공 메시지를 알림창으로 표시
        alert('회원가입이 되었습니다');
    }
