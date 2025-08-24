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
        DBInitializer.initialize("jdbc:sqlite:quiz.db");
        BoardDAO boardDAO = new BoardDAO("jdbc:sqlite:quiz.db");
        QuestionDAO questionDAO = new QuestionDAO("jdbc:sqlite:quiz.db");

        QuizService quizService = new QuizService(questionDAO);
        CardService cardService = new CardService(questionDAO, boardDAO);
        DeckService deckService = new DeckService(boardDAO, cardService);
        MenuActions menuActions = new MenuActions(quizService, deckService, cardService);

        AppController appController = new AppController(menuActions);
        appController.start();
    }
}
