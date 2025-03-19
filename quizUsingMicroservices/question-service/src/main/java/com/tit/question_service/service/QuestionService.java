package com.tit.question_service.service;


import com.tit.question_service.entity.Question;
import com.tit.question_service.entity.QuestionWrapper;
import com.tit.question_service.entity.Response;
import com.tit.question_service.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>( questionRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new  ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return  new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestion) {
        List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName, numQuestion);
        return  new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for (Integer id : questionIds){
            questions.add(questionRepository.findById(id).get());
        }

        for (Question question: questions){
            QuestionWrapper qw = new QuestionWrapper();
            qw.setId(question.getId());
            qw.setQuestionTitle(question.getQuestionTitle());
            qw.setOption1(question.getOption1());
            qw.setOption2(question.getOption2());
            qw.setOption3(question.getOption3());
            qw.setOption4(question.getOption4());
            wrappers.add(qw);
        }


        return new  ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;

        for (Response response : responses) {
            Question question = questionRepository.findById(response.getId()).get();
            if (response.getresponse().equals(question.getRightAnswer())) {
                right++;
            }

        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
