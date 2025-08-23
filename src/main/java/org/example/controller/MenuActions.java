package org.example.controller;

import org.example.Utils;
import org.example.entity.Deck;
import org.example.service.CardService;
import org.example.service.DeckService;
import org.example.service.QuizService;

import java.sql.SQLException;

public class MenuActions {

    private QuizService quizService;
    private DeckService deckService;
    private CardService cardService;

    public MenuActions(QuizService quizService, DeckService deckService, CardService cardService) {
        this.quizService = quizService;
        this.deckService = deckService;
        this.cardService = cardService;
    }

    public void startQuiz() throws SQLException {
        var deck = deckService.chooseDeck();
        quizService.study(deck);
    }

    public void manageDecks() throws SQLException {
        Utils.createPoints("Створення колоди", "Перегляд списку колод", "Управління картками");
        int answer = Utils.askInt(1, 3);
        switch (answer) {
            case 1 -> deckService.createDeck();
            case 2 -> {
                Deck deck = deckService.chooseDeck();
                System.out.println("Колода обрана!");
                deckService.chooseManageStep(deck);
            }
            case 3 -> manageCards();
            default -> System.out.println("Невідома команда.");
            }
        }

        public void manageCards () throws SQLException {
            Utils.createPoints("Додавання картки", "Перегляд карток", "Видалення картки");
            int answer = Utils.askInt(1,3);
            switch (answer){
                case 1 -> cardService.addCard();
                case 2 -> cardService.getCards();
                case 3 -> cardService.deleteById();
                default -> System.out.println("Невідома команда.");
            }
        }
    }


