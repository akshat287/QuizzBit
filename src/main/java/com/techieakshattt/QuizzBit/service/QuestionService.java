package com.techieakshattt.QuizzBit.service;

import com.techieakshattt.QuizzBit.dao.QuestionDao;
import com.techieakshattt.QuizzBit.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try {
            return new ResponseEntity(questionDao.findAll(), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ArrayList<>() , HttpStatus.BAD_REQUEST);
        }
    }




    public ResponseEntity<List<Question>> getByCategory(String cat) {
        try {
            return new ResponseEntity(questionDao.findByCategory(cat), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(new ArrayList<>() , HttpStatus.BAD_REQUEST);
        }

    }




    public ResponseEntity<String> addQuestion(List<Question> questions) {
        try{
            questionDao.saveAll(questions);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        return new ResponseEntity("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
