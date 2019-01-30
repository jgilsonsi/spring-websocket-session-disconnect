var stompClient = null;
var url = 'http://localhost:8080/ws';

function connect() {
    message('Starting websocket');

    var socket = new SockJS(url);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/mq', broadcastMessageReceiver);

    document.getElementById('connect').disabled = true;
    document.getElementById('connect').style.background = "green";
    message('Connected');

    setInterval(sendMessageToServer, 30000);
}

function onError(error) {
    document.getElementById('connect').disabled = false;
    document.getElementById('connect').style.background = "red";
    message('Could not connect to ws server.');
}

message = function (message) {
    var content = document.getElementById("broadcastResult");
    content.value = message + '\n\n' + content.value;
}

broadcastMessageReceiver = function (message) {
    var content = document.getElementById("broadcastResult");
    content.value = message.body + '\n\n' + content.value;
}

privateMessageReceiver = function (message) {
    var content = document.getElementById("privateResult");
    content.value = message.body + '\n\n' + content.value;
}

function sendMessageToServer() {
    stompClient.send("/app/message", {}, 'Hello user');
}