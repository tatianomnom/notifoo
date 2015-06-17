package com.leveluptor.notifoo;

import com.google.common.collect.HashMultimap
import com.google.common.collect.MultimapBuilder
import com.google.common.collect.Multimaps
import com.google.common.collect.SetMultimap
import java.util.HashSet
import java.util.concurrent.ConcurrentHashMap
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

private val subscribers : SetMultimap<String, Session> = Multimaps.synchronizedSetMultimap(HashMultimap.create())

@ServerEndpoint(value = "/channel/{channel}")
public class MyEndpoint {

    @OnOpen
    public fun onOpen(@PathParam("channel") channel: String, session: Session) {

        subscribers.put(channel, session)

        val text = "[$channel] User ${session.getId()} joined"
        println(text)

        subscribers.get(channel).forEach { s -> s.getBasicRemote().sendText(text) }
    }

    @OnMessage
    public fun onMessage(message: String, @PathParam("channel") channel: String, session: Session) {
        val text = "[$channel] User ${session.getId()}: $message"
        println(text)

        subscribers.get(channel).forEach { s -> s.getBasicRemote().sendText(text) }
    }

    @OnClose
    public fun onClose(@PathParam("channel") channel: String, session: Session) {
        val text = "[$channel] User ${session.getId()} left"
        println(text)
        subscribers.get(channel).forEach { s -> s.getBasicRemote().sendText(text) }

        subscribers.remove(channel, session)
    }

}
