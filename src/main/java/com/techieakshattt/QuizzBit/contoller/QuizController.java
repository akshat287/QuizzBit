package com.techieakshattt.QuizzBit.contoller;

import com.techieakshattt.QuizzBit.model.QuestionWrapper;
import com.techieakshattt.QuizzBit.model.Response;
import com.techieakshattt.QuizzBit.model.Result;
import com.techieakshattt.QuizzBit.service.QuizService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int num, @RequestParam String title){
        return quizService.createQuiz(category,num,title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer id){
        return quizService.getQuizById(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Result> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
