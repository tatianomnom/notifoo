package com.leveluptor.notifoo;

import java.util.HashSet
import java.util.concurrent.ConcurrentHashMap
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

private val subscribers = ConcurrentHashMap<String, Set<Session>>()

@ServerEndpoint(value = "/channel/{channel}")
public class MyEndpoint {

    @OnOpen
    public fun onOpen(@PathParam("channel") channel: String, session: Session) {
        (subscribers.getOrPut(channel, { hashSetOf() }) as HashSet).add(session)

        val text = "[$channel] User ${session.getId()} joined"
        println(text)

        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText(text) }
    }

    @OnMessage
    public fun onMessage(message: String, @PathParam("channel") channel: String, session: Session) {
        val text = "[$channel] User ${session.getId()}: $message"
        println(text)
        subscribers.getOrDefault(channel, emptySet()).forEach { s -> s.getBasicRemote().sendText(text) }
    }

    @OnClose
    public fun onClose(@PathParam("channel") channel: String, session: Session) {
        val text = "[$channel] User ${session.getId()} left"
        println(text)
        session.getOpenSessions().forEach { s -> s.getBasicRemote().sendText(text) }
        (subscribers.getOrPut(channel, { hashSetOf() }) as HashSet).remove(session)
    }

}
