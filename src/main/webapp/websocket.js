var WS = function () {

    var sockets = {};

    return  {
        disconnect: function (name) {
            if (name in sockets) sockets[name].close();
            document.getElementById('log').removeChild(document.getElementById(name))
        },
        send: function (name, msg) {
            if (name in sockets) sockets[name].send(msg)
        },
        connectChannel: function(name) {
            var socket = new WebSocket("ws://localhost:8080/notifoo/channel/" + name);
            var channelDiv = document.createElement("div");
            channelDiv.id = name;
            document.getElementById('log').appendChild(channelDiv);
            socket.onmessage = function(event) {
                var entry = document.createElement("p");
                entry.innerHTML = event.data;
                document.getElementById(name).appendChild(entry);
            };
            sockets[name] = socket;
        }
    };
}();