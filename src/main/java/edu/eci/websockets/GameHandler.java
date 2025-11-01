package edu.eci;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Component
public class GameHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private String[] board = new String[9];
    private boolean xIsNext = true;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("Nueva conexión: " + session.getId());
        sendBoardTo(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensaje recibido: " + payload);

        
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = mapper.readValue(payload, Map.class);

        String type = (String) data.get("type");

        switch (type) {
            case "RESET":
                Arrays.fill(board, null);
                xIsNext = true;
                break;

            case "MOVE":
                int index = (int) data.get("index");
                if (board[index] == null) {
                    board[index] = xIsNext ? "X" : "O";
                    xIsNext = !xIsNext;
                }
                break;
        }

        broadcastBoard();
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("Conexión cerrada: " + session.getId());
    }

    private void broadcastBoard() throws Exception {
        for (WebSocketSession s : sessions) {
            sendBoardTo(s);
        }
    }

    private void sendBoardTo(WebSocketSession session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> gameState = new HashMap<>();
            gameState.put("type", "MOVE");
            gameState.put("board", board);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(gameState)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
