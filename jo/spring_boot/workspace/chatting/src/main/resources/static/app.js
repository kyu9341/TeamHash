var stompClient = null;


function connect() { // 생성된 소켓과 연결
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chat', function(chat){ // 구독 기능을 통해 해당 주소에 메세지가 오면 showChat 함수 호출
            showChat(JSON.parse(chat.body));
        })
    });
}
function disconnect() { // 연결 종료
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function sendChat(){ // 메세지 전송 index.html의 text box에 입력된 값을 읽어온다.
    stompClient.send("/app/chat",{},JSON.stringify({'sender':$("#sender").val(), 'message': $("#chatMessage").val() }))
}

function showChat(chat) { // 수신한 메세지를 출력
                          // DB에 저장된 값을 출력해주는 Thymeleaf와 형태를 맞추기 위해 
                          // 적절한 배치 필요

    var sendDateTime = chat.sendDateTime.split("T"); // 시간과 분 출력 
    sendDateTime = sendDateTime[1].split(":");
    $("#chattings").append("<tr><td><span>"+chat.sender+
    "</span></td><td><span>"+chat.message+
    "</span></td><td><span>"+sendDateTime[0]+":"+sendDateTime[1]+
    "</span></td></tr>");
}


$(function () { // 함수 호출 연결
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#chatSend").click(function(){ sendChat();});
});