package com.tit.quiz_service.service;


import com.tit.quiz_service.entity.QuestionWrapper;
import com.tit.quiz_service.entity.Quiz;
import com.tit.quiz_service.entity.Response;
import com.tit.quiz_service.feign.QuizInterface;
import com.tit.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> create(String category, int numQ, String title) {
        try {
           // Fetch questions based on category
           List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
//
//            // Check if questions exist
            if (questions.isEmpty()) {
                return new ResponseEntity<>("No questions found for category: " + category, HttpStatus.NOT_FOUND);
            }
//
//            // Create new quiz
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestionsIds(questions);

            // Save to database
            quizRepository.save(quiz);

            return new ResponseEntity<>("Quiz created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
      Quiz quiz = quizRepository.findById(id).get();
      List<Integer> questionIds = quiz.getQuestionsIds();
      ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromId(questionIds);
      return  questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
