package com.tit.quiz_service.feign;

import com.tit.quiz_service.entity.QuestionWrapper;
import com.tit.quiz_service.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName , @RequestParam int numQuestion);

    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("questions/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
