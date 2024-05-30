function checkUsername() {
    var username = document.getElementById('userid').value;
    $.ajax({
        url: '/api/check-username',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({userid: username}),  // JSON 객체로 변환
        success: function(response) {
            document.getElementById('useridStatus').innerText = response;
        },
        error: function() {
            alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
}