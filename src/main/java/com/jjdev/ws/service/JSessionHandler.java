package com.jjdev.ws.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class JSessionHandler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public JSessionHandler() {

        System.out.println("starting services");

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("\nregistered users: " + sessionMap.size());
            sessionMap.keySet().forEach(k -> System.out.println(k));
        }, 10, 10, TimeUnit.SECONDS);

        // removing users by time
        scheduler.scheduleAtFixedRate(() -> {
            unregisterSession(sessionMap.entrySet().iterator().next().getKey());
        }, 1, 1, TimeUnit.MINUTES);

    }

    public void registerSession(WebSocketSession session) {
        System.out.println("registering user: " + session.getId());
        sessionMap.put(session.getId(), session);
    }

    public void unregisterSession(String session) {
        System.out.println("\nremoving registered user: " + session);

        if (sessionMap.containsKey(session)) {
            try {
                sessionMap.get(session).close();
                sessionMap.remove(session);
            } catch (IOException ex) {
            }
        }
    }

}
