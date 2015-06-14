var WS = function () {

    var socket;

    return  {
        connect: function () {
            socket = new WebSocket("ws://localhost:8080/notifoo/demo");
            socket.onmessage = function(event) {
                var entry = document.createElement("p");
                entry.innerHTML = event.data;
                document.getElementById('log').appendChild(entry);
            }
        },
        disconnect: function () {
            socket.close()
        },
        send: function (msg) {
            socket.send(msg)
        }
    };
}();