package org.example.controller;

import org.example.Utils;
import java.sql.SQLException;

public class AppController {

    private final MenuActions menuActions;

    public AppController(MenuActions menuActions) {
        this.menuActions = menuActions;
    }

    public void start() throws SQLException {
        boolean running = true;
        greeting();
        while(running) {
            System.out.println("\nОберіть дію:");
            Utils.createPoints("Почати вивчення", "Управління колодами", "Вихід");
            int answer = Utils.askInt(1, 3);
            switch (answer) {
                case 1 -> menuActions.startQuiz();
                case 2 -> menuActions.manageDecks();
                case 3 -> {
                    running = false;
                }
                default -> System.out.println("Невідома команда.");
            }
        }
    }

    public void greeting(){
        System.out.println("Доброго дня!");
        System.out.println("Вітаю в консольному додатку Flashcard Quiz!");
    }

}
