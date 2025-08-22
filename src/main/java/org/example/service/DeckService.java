package org.example.service;

import org.example.Utils;
import org.example.database.BoardDAO;
import org.example.entity.Deck;

import java.sql.SQLException;

public class DeckService {

    private BoardDAO boardDAO;
    private CardService cardService;

    public DeckService(BoardDAO boardDAO, CardService cardService) {
        this.boardDAO = boardDAO;
        this.cardService = cardService;
    }

    public void createDeck() throws SQLException {
        String line = Utils.askLine("Введіть назву нової колоди:");
        boardDAO.createDeckDAO(line);
        System.out.println("Колода " + line + " була успішно створена." +
                "\nОберіть наступний крок:");
        chooseManageStep(boardDAO.getDeckByName(line));

    }


    public Deck chooseDeck(){
        var boards = boardDAO.getAllBoards();
        if (boards.isEmpty()) {
            System.out.println("Колод немає. Створіть спочатку колоду.");
            return null;
        }
        int i = 1;
        for (String b : boards) {
            System.out.println(i + ") " + b);
            i++;
        }
        int answer = Utils.askInt(1, i - 1);

        String chosenName = boards.get(answer - 1);
        if (chosenName == null) {
            System.out.println("Вибрана колода не існує.");
            return null;
        }

        return BoardDAO.getDeckByName(chosenName);

    }



    public void chooseManageStep(Deck deck) throws SQLException {
        System.out.println("Оберіть дію:");
        Utils.createPoints("Додати картку до колоди", "Перегляд карток", "Видалити колоду");
        int answer_second = Utils.askInt(1, 3);
        switch (answer_second) {
            case 1 -> cardService.addCardByDeck(deck);
            case 2 -> cardService.getDeckCards(deck);
            case 3 -> boardDAO.deleteDeck(deck.getId());
            default -> System.out.println("Невідома команда.");
        }
    }
}
