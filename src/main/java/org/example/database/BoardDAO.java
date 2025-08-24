package org.example.database;

import org.example.entity.Deck;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private final String URL;

    public BoardDAO(String URL) {
        this.URL = URL;
    }

    public void createDeckDAO(String name) {
        String sql = "INSERT INTO boards(name) VALUES(?)";

        try (Connection conn = DBInitializer.connect(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Deck getDeckById(int deckId) {
        String sql = "SELECT id, name FROM boards WHERE id = ?";
        try (Connection conn = DBInitializer.connect(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, deckId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Deck(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Deck getDeckByName(String name) {
        String sql = "SELECT id, name FROM boards WHERE name = ?";

        try (Connection conn = DBInitializer.connect(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Deck(rs.getInt("id"), rs.getString("name"));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteDeck(int deckId) {
        String sql = "DELETE FROM boards WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, deckId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Колода номер " + deckId + " видалена");
            } else {
                System.out.println("Колода номер " + deckId + " не існує");
            }
        } catch (SQLException e) {
            System.out.println("Помилка при видаленні: " + e.getMessage());
        }
    }

    public List<String> getAllBoards() {
        List<String> boards = new ArrayList<>();

        String sql = "SELECT name FROM boards";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                boards.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }
}
