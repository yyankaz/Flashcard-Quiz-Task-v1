import org.example.database.BoardDAO;
import org.example.database.QuestionDAO;
import org.example.entity.Deck;
import org.example.entity.Question;
import org.example.service.CardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardServiceTest {

    private static QuestionDAO questionDAO;
    private static BoardDAO boardDAO;

    private CardService cardService;

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

        questionDAO = new QuestionDAO(TEST_DB);
        boardDAO = new BoardDAO(TEST_DB);
    }

    @BeforeEach
    void setUp() {
        cardService = new CardService(questionDAO, boardDAO);
    }

    @Test
    void testAddCard() throws SQLException {
        Deck deck = new Deck(1, "Test Deck");
        try (Connection conn = DriverManager.getConnection(TEST_DB);
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO boards (id, name) VALUES (1, 'Test Deck')");
        }

        questionDAO.addQuestion("2+2?", "4", deck);

        List<Question> questions = questionDAO.getQuestionsByBoardId(deck.getId());

        assertEquals(1, questions.size());
        assertEquals("2+2?", questions.getFirst().getQuestionText());
        assertEquals("4", questions.getFirst().getAnswer());
    }
}
