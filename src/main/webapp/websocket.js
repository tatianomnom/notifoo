var WS = function () {

    var sockets = {};

    return  {
        disconnect: function (name) {
            if (name in sockets) sockets[name].close();
        },
        send: function (name, msg) {
            if (name in sockets) sockets[name].send(msg)
        },
        connectChannel: function(name) {
            var socket = new WebSocket("ws://localhost:8080/notifoo/channel/" + name);
            socket.onmessage = function(event) {
                var entry = document.createElement("p");
                entry.innerHTML = event.data;
                document.getElementById('log').appendChild(entry);
            };
            sockets[name] = socket;

            var channelEntry = document.createElement('button');
            channelEntry.innerHTML = 'Leave ' + name
            channelEntry.onclick = function () {
                WS.disconnect(name)
                this.parentNode.removeChild(this)
            }

            document.getElementById('channels').appendChild(channelEntry)
        }
    };
}();