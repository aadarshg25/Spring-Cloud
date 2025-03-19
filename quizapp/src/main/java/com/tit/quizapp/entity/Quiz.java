package com.tit.quizapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @JoinTable(
            name = "quiz_question_mapping",  // Custom join table name
            joinColumns = @JoinColumn(name = "quiz_id"),  // Column referring to Quiz
            inverseJoinColumns = @JoinColumn(name = "question_id") // Column referring to Question
    )
    @ManyToMany
    private List<Question> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for questions
    public List<Question> getQuestions() {
        return questions;
    }

    // Setter for questions
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
