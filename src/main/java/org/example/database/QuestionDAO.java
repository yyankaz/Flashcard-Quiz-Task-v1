package org.example.database;

import org.example.entity.Deck;
import org.example.entity.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private static final String URL = "jdbc:sqlite:quiz.db";

    public void addQuestion(String text, String answer, Deck deck) throws SQLException {
        String sql = "INSERT INTO questions (text, answer, deck_id) VALUES (?, ?, ?)";

        try (Connection conn = DBInitializer.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, text);
            pstmt.setString(2, answer);
            pstmt.setInt(3, deck.getId());

            pstmt.executeUpdate();
            System.out.println("Питання успішно додано!");
        }
    }

    public void deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Запитання за номером " + id + " видалене");
            } else {
                System.out.println("Запитання за номером " + id + " не існує");
            }

        } catch (SQLException e) {
            System.out.println("Помилка при видаленні: " + e.getMessage());
        }
    }

    public List<Question> getQuestionsByBoardId(int deckId) throws SQLException {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT id, deck_id, text, answer FROM questions WHERE deck_id = ?";

        try (Connection conn = DBInitializer.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, deckId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Question q = new Question(
                            rs.getInt("id"),
                            rs.getInt("deck_id"),
                            rs.getString("text"),
                            rs.getString("answer")
                    );
                    questions.add(q);
                }
            }
        }
        return questions;
    }

}
