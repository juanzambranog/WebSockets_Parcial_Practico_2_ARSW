package edu.eci.websockets.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @GetMapping("/")
    public String home() {
        return "Servidor en línea";
    }

    @GetMapping("/status")
    public String status() {
        return "Servidor TicTacToe en línea";
    }

    @GetMapping("/board")
    public String getBoard() {
        return String.join(",", getBoard());
    }
}
