package com.tit.quizapp.service;


import com.tit.quizapp.entity.Question;
import com.tit.quizapp.entity.QuestionWrapper;
import com.tit.quizapp.entity.Quiz;
import com.tit.quizapp.entity.Response;
import com.tit.quizapp.repository.QuestionRepository;
import com.tit.quizapp.repository.QuizRepository;
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
    QuestionRepository questionRepository;

    public ResponseEntity<String> create(String category, int numQ, String title) {
        try {
            // Fetch questions based on category
            List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

            // Check if questions exist
            if (questions.isEmpty()) {
                return new ResponseEntity<>("No questions found for category: " + category, HttpStatus.NOT_FOUND);
            }

            // Create new quiz
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            // Save to database
            quizRepository.save(quiz);

            return new ResponseEntity<>("Quiz created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questionFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionFromDb){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),q.getQuestionTitle());
            questionForUser.add(qw);
        }


        return  new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (!optionalQuiz.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;

        for (Response response : responses) {
            if (i < questions.size() && response.getresponse().equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
