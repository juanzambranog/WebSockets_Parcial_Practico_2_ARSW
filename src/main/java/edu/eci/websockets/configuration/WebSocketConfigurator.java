package edu.eci.websockets.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import edu.eci.websockets.GameHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfigurator implements WebSocketConfigurer {

    private final GameHandler gameHandler;

    public WebSocketConfigurator(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameHandler, "/game").setAllowedOrigins("*");
    }
}
