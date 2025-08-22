package org.example;

import org.example.controller.AppController;
import org.example.controller.MenuActions;
import org.example.database.BoardDAO;
import org.example.database.DBInitializer;
import org.example.database.QuestionDAO;
import org.example.service.CardService;
import org.example.service.DeckService;
import org.example.service.QuizService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBInitializer.initialize();
        BoardDAO boardDAO = new BoardDAO();
        QuestionDAO questionDAO = new QuestionDAO();

        QuizService quizService = new QuizService(questionDAO);
        CardService cardService = new CardService(questionDAO);
        DeckService deckService = new DeckService(boardDAO, cardService);
        MenuActions menuActions = new MenuActions(quizService, deckService, cardService);

        AppController appController = new AppController(menuActions);
        appController.start();
    }
}
