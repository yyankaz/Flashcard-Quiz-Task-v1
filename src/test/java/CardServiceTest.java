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

    public CardServiceTest() throws SQLException {
    }

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
        boardDAO.createDeckDAO("Test Deck");
        try (Connection conn = DriverManager.getConnection(TEST_DB);
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO boards (name) VALUES ('Test Deck')");
        }

        questionDAO.addQuestion("2+2?", "4", boardDAO.getDeckByName("Test Deck"));

        List<Question> questions = questionDAO.getQuestionsByBoardId(boardDAO.getDeckByName("Test Deck").getId());

        assertEquals(1, questions.size());
        assertEquals("2+2?", questions.get(0).getQuestionText());
        assertEquals("4", questions.get(0).getAnswer());
    }

    @Test
    void testDeleteById() throws SQLException {
        boardDAO.createDeckDAO("Test Deck_2");
        try (Connection conn = DriverManager.getConnection(TEST_DB);
             Statement stmt = conn.createStatement()) {
            stmt.execute("INSERT INTO boards (name) VALUES ('Test Deck_2')");
        }

        questionDAO.addQuestion("2+3?", "5", boardDAO.getDeckByName("Test Deck_2"));

        List<Question> questions = questionDAO.getQuestionsByBoardId(boardDAO.getDeckByName("Test Deck_2").getId());
        assertFalse(questions.isEmpty());
        questionDAO.deleteQuestion(questions.getFirst().getId());
        List<Question> questionsAfter = questionDAO.getQuestionsByBoardId(boardDAO.getDeckByName("Test Deck_2").getId());
        assertTrue(questionsAfter.isEmpty());
    }

}

