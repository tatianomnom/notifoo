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

            var channelEntry = document.createElement('li');
            var channelLabel = document.createElement('span');
            var channelButton = document.createElement('button');
            channelLabel.innerHTML = name;
            channelButton.innerHTML = 'Leave';
            channelButton.onclick = function () {
                WS.disconnect(name);
                channelEntry.parentNode.removeChild(channelEntry)
            };
            channelEntry.appendChild(channelLabel);
            channelEntry.appendChild(channelButton);

            document.getElementById('channelList').appendChild(channelEntry)
        }
    };
}();