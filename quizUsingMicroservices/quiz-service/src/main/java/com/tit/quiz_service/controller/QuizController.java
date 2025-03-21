package com.tit.quiz_service.controller;


import com.tit.quiz_service.dto.QuizDto;
import com.tit.quiz_service.entity.QuestionWrapper;
import com.tit.quiz_service.entity.Response;
import com.tit.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class  QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.create(quizDto.getCategoryName(),quizDto.getNumQuestion(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
     return  quizService.getQuizQuestions(id);
    }

    @PostMapping("sumbit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return  quizService.calculateResult(id,responses);
    }
}
