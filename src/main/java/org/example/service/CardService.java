package org.example.service;

import org.example.Utils;
import org.example.database.BoardDAO;
import org.example.database.QuestionDAO;
import org.example.entity.Deck;
import org.example.entity.Question;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CardService {

    private final QuestionDAO questionDAO;

    public CardService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void addCard() throws SQLException {
        String text = Utils.askLine("Будь ласка, введіть запитання:");
        String rightAnswer = Utils.askLine("Будь ласка, введіть правильну відповідь на ваше запитання:");
        String deckName = Utils.askLine("Будь ласка, введіть назву колоди, до якої Ви хочете додати це запитання:");

        Deck deck = BoardDAO.getDeckByName(deckName);
        if (deck == null) {
            System.out.println("Колода з назвою '" + deckName + "' не існує. Створіть її спочатку.");
            return;
        }

        questionDAO.addQuestion(text, rightAnswer, Objects.requireNonNull(BoardDAO.getDeckByName(deckName)));
    }

    public void addCardByDeck(Deck deck) throws SQLException {
        String text = Utils.askLine("Будь ласка, введіть запитання:");
        String rightAnswer = Utils.askLine("Будь ласка, введіть правильну відповідь на ваше запитання:");
        questionDAO.addQuestion(text, rightAnswer, deck);
    }

    public void deleteById(){
        int cardId = Utils.askIntWithoutLimit("Будь ласка, введіть номер запитання: ");
        questionDAO.deleteQuestion(cardId);
    }

    public void getCards() throws SQLException {
        int deckId = Utils.askIntWithoutLimit("Будь ласка, введіть номер колоди: ");
        List<Question> questions = questionDAO.getQuestionsByBoardId(deckId);
        for (Question q : questions) {
            System.out.println(q.getId() + ") " + q.getQuestionText());
            System.out.println("----");

        }
    }

    public void getDeckCards(Deck deck) throws SQLException {
        List<Question> questions = questionDAO.getQuestionsByBoardId(deck.getId());
        for (Question q : questions) {
            System.out.println(q.getId() + ") " + q.getQuestionText());
            System.out.println("----");
        }
    }
}
