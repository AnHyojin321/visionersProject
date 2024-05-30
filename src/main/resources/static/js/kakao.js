document.addEventListener("DOMContentLoaded", function() {
    // 카카오 SDK 초기화
    Kakao.init('d029a980ccfe9dcd1f945f060919fad6'); // 여기에 실제 카카오 앱의 JavaScript 키를 사용하세요.

    document.getElementById('kakao-login-btn').addEventListener('click', function() {
        Kakao.Auth.login({
            success: function(authObj) {
                // 로그인 성공 후 서버로 액세스 토큰 전송
                fetch('/login/oauth2/code/kakao', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        accessToken: authObj.access_token
                    })
                })
                .then(response => response.json())
                .then(data => {
                    // 서버로부터 받은 데이터를 기반으로 추가 작업
                    console.log(data);
                    window.location.href = '/main'; // main 페이지로 리다이렉트
                })
                .catch(error => console.error('Error:', error));
            },
            fail: function(err) {
                console.error(err);
            }
        });
    });
});
