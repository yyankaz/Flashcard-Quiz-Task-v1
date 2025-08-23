import org.example.database.QuestionDAO;
import org.example.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.service.QuizService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizServiceTest {

    @Mock
    private QuestionDAO questionDAO;

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizService(questionDAO);
    }

    @Test
    void testGetMark() throws SQLException {
        QuizService quizService = new QuizService(questionDAO);
        List<Question> questions = List.of(
                new Question(1, 1, "2 + 7 = ", "9"),
                new Question(2, 1, "12 - 8 = ", "4"),
                new Question(3,1, "11 - 3 = ", "8"),
                new Question(4, 1, "Назвіть столицю України", "Київ")
        );

        List<String> answers = List.of("9 ", " 4", "8", "київ");
        int result = quizService.getMark(questions, answers);

        assertEquals(4, result);
    }
}
