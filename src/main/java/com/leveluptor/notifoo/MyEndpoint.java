package com.leveluptor.notifoo;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/demo")
public class MyEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " connected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(session.getId() + " sent " + message);
        try {
            session.getBasicRemote().sendText(session.getId() + " sent " + message);

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session.getId() + " disconnected");
    }

}
