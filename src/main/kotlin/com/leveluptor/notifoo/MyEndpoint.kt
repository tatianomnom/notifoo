package com.leveluptor.notifoo;

import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(value = "/channel/{channel}")
public class MyEndpoint {

    @OnOpen
    public fun onOpen(@PathParam("channel") channel: String, session: Session) {
        println(session.getId() + " connected")
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText("[$channel] User ${session.getId()} joined") }
    }

    @OnMessage
    public fun onMessage(message: String, @PathParam("channel") channel: String, session: Session) {
        println(session.getId() + " sent " + message)
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText("[$channel] User ${session.getId()}: $message") }
    }

    @OnClose
    public fun onClose(@PathParam("channel") channel: String, session: Session) {
        println(session.getId() + " disconnected")
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText("[$channel] User ${session.getId()} left") }
    }

}
