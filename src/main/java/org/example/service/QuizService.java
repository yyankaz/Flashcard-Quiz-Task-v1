package org.example.service;

import org.example.Utils;
import org.example.database.QuestionDAO;
import org.example.entity.Deck;
import org.example.entity.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizService {

    private QuestionDAO questionDAO;

    public QuizService(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public void study(Deck deck) throws SQLException {
        List<Question> questions = questionDAO.getQuestionsByBoardId(deck.getId());
        System.out.println("Ви обрали колоду " + deck.getTitle() + ". " +
                "\nНаразі вона складається з " + questions.size() + " запитань. \n");
        List<String> answers = getAnswers(questions);
        int mark = getMark(questions, answers);
        double mark_100 = getMark_100(questions, mark);
            System.out.println("Вітаю! Ви завершили тест!\n" +
                    "\nКількість правильних відповідей: " + mark + "\n" +
                    "\nВаша оцінка: " + mark_100);
    }

    public List<String> getAnswers(List<Question> questions){
        List<String> answers = new ArrayList<>();
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            System.out.println("----");
            String answer = Utils.askLine("Ваша відповідь:");
            System.out.println("----");
            answers.add(answer);
        }
        return answers;
    }

    public int getMark(List<Question> questions, List<String> answers){
        int mark = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (answers.get(i).trim().equalsIgnoreCase(questions.get(i).getAnswer())) {
                mark++;
            }
        }
        return mark;
    }

    public double getMark_100(List<Question> questions, int correctAnswers){
        return ((double) 100 /questions.size())*correctAnswers;
    }

}
