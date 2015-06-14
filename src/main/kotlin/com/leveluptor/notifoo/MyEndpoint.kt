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
        println(session.getId() + " connected")
    }

    @OnMessage
    public fun onMessage(message: String, session: Session) {
        println(session.getId() + " sent " + message)
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText("[User ${session.getId()}]: $message") }
    }

    @OnClose
    public fun onClose(session: Session) {
        println(session.getId() + " disconnected")
    }

}
