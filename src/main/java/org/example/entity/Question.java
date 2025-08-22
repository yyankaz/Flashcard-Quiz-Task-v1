package org.example.entity;

public class Question {
    private int id;
    private String text;
    private String answer;
    private int deckId;

    public Question(int id, int deckIdId, String text, String answer) {
        this.id = id;
        this.deckId = deckIdId;
        this.text = text;
        this.answer = answer;
    }

    public int getId() { return id; }
    public int getBoardId() { return deckId; }
    public String getQuestionText() { return text; }
    public String getAnswer() { return answer; }
}
