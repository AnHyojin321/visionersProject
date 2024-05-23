document.addEventListener('DOMContentLoaded', function() {
    console.log("Document loaded and script running."); // 문서가 로드되고 스크립트가 실행 중인지 확인

    function handleServiceUse() {
        console.log("Button clicked."); // 버튼이 클릭되었는지 확인
        alert("로그인 후 이용하세요");
        window.location.href = "/login"; // 로그인 페이지의 URL로 이동
    }

    var button = document.getElementById('service-button');
    if (button) {
        button.addEventListener('click', handleServiceUse);
        console.log("Event listener added to button."); // 이벤트 리스너가 버튼에 추가되었는지 확인
    } else {
        console.error("Button with ID 'service-button' not found.");
    }
});
