package org.example.service;

import org.example.Utils;
import org.example.database.QuestionDAO;
import org.example.entity.Deck;
import org.example.entity.Question;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizService {

    private QuestionDAO questionDAO;

    public QuizService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void study(Deck deck) throws SQLException {
        List<Question> questions = questionDAO.getQuestionsByBoardId(deck.getId());
        int mark = 0;
        double mark100;
        System.out.println("Ви обрали колоду " + deck.getTitle() + ". " +
                "\nНаразі вона складається з " + questions.size() + " запитань. \n");
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            System.out.println("----");
            String answer = Utils.askLine("Ваша відповідь:");
            System.out.println("----");
            if (answer.trim().equalsIgnoreCase(question.getAnswer())) {
                mark++;
            }
        }
            mark100 = ((double) 100 /questions.size())*mark;
            System.out.println("Вітаю! Ви завершили тест!\n" +
                    "\nКількість правильних відповідей: " + mark + "\n" +
                    "\nВаша оцінка: " + mark100);
    }

}
