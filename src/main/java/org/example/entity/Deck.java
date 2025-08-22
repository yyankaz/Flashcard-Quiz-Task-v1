package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private int id;
    private String title;
    private List<Question> questions;

    public Deck(int id, String title) {
        this.id = id;
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
