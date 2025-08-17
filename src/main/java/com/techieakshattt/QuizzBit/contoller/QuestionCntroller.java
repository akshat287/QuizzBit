package com.techieakshattt.QuizzBit.contoller;


import com.techieakshattt.QuizzBit.model.Question;
import com.techieakshattt.QuizzBit.service.QuestionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Question")
public class QuestionCntroller {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/AllQuestion")
    public ResponseEntity<List<Question>> GetAllQuestion(){
        return questionService.getAllQuestion();
    }

    @GetMapping("/category/{cat}")
    public ResponseEntity<List<Question>> GetQuestionByCategory(@PathVariable String cat){
        return questionService.getByCategory(cat);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody List<Question> questions){
        return questionService.addQuestion(questions);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
}
