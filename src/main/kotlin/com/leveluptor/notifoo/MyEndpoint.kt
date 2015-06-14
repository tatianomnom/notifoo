package com.leveluptor.notifoo;

import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value = "/demo")
public class MyEndpoint {

    @OnOpen
    public fun onOpen(session: Session) {
        print(session.getId() + " connected")
    }

    @OnMessage
    public fun onMessage(message: String, session: Session) {
        print(session.getId() + " sent " + message)
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText("Someone with id " + session.getId() + " sent " + message) }
    }

    @OnClose
    public fun onClose(session: Session) {
        print(session.getId() + " disconnected")
    }

}
