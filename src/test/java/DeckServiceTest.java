import org.example.database.BoardDAO;
import org.example.entity.Deck;
import org.example.service.DeckService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class DeckServiceTest {

    private static BoardDAO boardDAO;
    private static DeckService deckService;

    static final String TEST_DB = "jdbc:sqlite:file:memdb1?mode=memory&cache=shared";

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        Connection conn = DriverManager.getConnection(TEST_DB);
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS boards (\n" +
                "                    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                    name TEXT NOT NULL\n" +
                "                )");
        stmt.execute("                CREATE TABLE IF NOT EXISTS questions (\n" +
                "                    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "                    deck_id INTEGER NOT NULL,\n" +
                "                    text TEXT NOT NULL,\n" +
                "                    answer TEXT NOT NULL,\n" +
                "                    FOREIGN KEY (deck_id) REFERENCES boards(id)\n" +
                "                )");

        boardDAO = new BoardDAO(TEST_DB);
        deckService = new DeckService(boardDAO, null);
    }

    @Test
    void testCreateDeck() {
        boardDAO.createDeckDAO("Test Deck");

        try (Connection conn = DriverManager.getConnection(TEST_DB);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM boards WHERE name = ?")) {

            pstmt.setString(1, "Test Deck");
            ResultSet rs = pstmt.executeQuery();


            assertTrue(rs.next(), "Колоду потрібно зробити");


            assertEquals("Test Deck", rs.getString("name"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testChooseDeck(){
        boardDAO.createDeckDAO("Test Deck_2");
        boardDAO.createDeckDAO("Test Deck_3");
        boardDAO.createDeckDAO("Test Deck_4");

        try (Connection conn = DriverManager.getConnection(TEST_DB);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM boards WHERE name = ?")) {

            pstmt.setString(1, "Test Deck_2");
            pstmt.setString(1, "Test Deck_3");
            pstmt.setString(1, "Test Deck_4");

            ResultSet rs = pstmt.executeQuery();

            assertTrue(rs.next(), "Колоду потрібно зробити");

            Deck resultDeck = deckService.chooseDeck(3);

            assertEquals("Test Deck_3", resultDeck.getTitle());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
