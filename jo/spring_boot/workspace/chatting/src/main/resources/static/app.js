var stompClient = null;


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);


    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/chat', function(chat){
            showChat(JSON.parse(chat.body));
        })
    });


}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function sendChat(){
    stompClient.send("/app/chat",{},JSON.stringify({'sender':$("#sender").val(), 'message': $("#chatMessage").val() }))
}



function showChat(chat) {
    
    $("#chattings").append("<tr><td>" + chat.sender + " : " + chat.message + "</td></tr>");
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#chatSend").click(function(){ sendChat();});
});