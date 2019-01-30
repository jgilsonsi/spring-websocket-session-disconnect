package com.jjdev.ws.event;

import com.jjdev.ws.service.JSessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

/**
 *
 * @author jgilson
 */
public class JCustomSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler {
    
    @Autowired
    private JSessionHandler sessionHandler;
    
    public JCustomSubProtocolWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
        super(clientInboundChannel, clientOutboundChannel);
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("new websocket connection was established: " + session.getId());
        sessionHandler.registerSession(session);
        super.afterConnectionEstablished(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("websocket connection was closed: " + session.getId() + " " + closeStatus.toString());
        sessionHandler.unregisterSession(session.getId());
        super.afterConnectionClosed(session, closeStatus);
    }
    
}
