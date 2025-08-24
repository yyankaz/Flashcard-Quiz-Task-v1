import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.example.service.QuizService;
import org.example.database.QuestionDAO;
import org.example.entity.Question;

public class QuizServiceTest {

    @Mock
    private QuestionDAO questionDAO;

    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizService(questionDAO);
    }

    List<Question> questions = List.of(
            new Question(1, 1, "2 + 7 = ", "9"),
            new Question(2, 1, "12 - 8 = ", "4"),
            new Question(3,1, "11 - 3 = ", "8"),
            new Question(4, 1, "Назвіть столицю України", "Київ")
    );

    List<String> answers = List.of("9 ", " 4", "8", "киЇв");

    @Test
    void testGetMark(){
        int result = quizService.getMark(questions, answers);
        assertEquals(4, result);
    }

    @Test
    void testGetMark_100(){
        double result1 = quizService.getMark_100(questions, 4);
        double result2 = quizService.getMark_100(questions, 1);
        double result3 = quizService.getMark_100(questions, 0);
        assertEquals(100, result1);
        assertEquals(25, result2);
        assertEquals(0, result3);
    }
}
