package com.techieakshattt.QuizzBit.service;

import com.techieakshattt.QuizzBit.dao.QuestionDao;
import com.techieakshattt.QuizzBit.dao.QuizDao;
import com.techieakshattt.QuizzBit.model.*;
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
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String categaory, int num, String title) {

        List<Question> questions=questionDao.FindRandomQuestionByCategory(categaory,num);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(int id) {
        try{
            Optional<Quiz> quiz=quizDao.findById(id);
            if(!quiz.isPresent())return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<Question> questionfromDB=quiz.get().getQuestions();
            List<QuestionWrapper> QuestionforQuiz=new ArrayList<>();
            for(Question q:questionfromDB){
                QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
                QuestionforQuiz.add(qw);
            }

            return new ResponseEntity(QuestionforQuiz,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    public ResponseEntity<Result> calculateResult(Integer id, List<Response> responses) {
//        try{
//            Optional<Quiz> quiz=quizDao.findById(id);
//            if(!quiz.isPresent())return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//            List<Question> questions=quiz.get().getQuestions();
//            int right=0;
//            for(Response rep:responses){
//               Question q=questions.stream()
//                       .filter(ques->ques.getId().equals(rep.getQuestionId()))
//                       .findFirst()
//                       .orElse(null);
//               if(q!=null && rep.getResponse().equals(q.getCorrectAnswer()))right++;
//            }
//            return new ResponseEntity<>(new Result(right,questions.size()),HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    public ResponseEntity<Result> calculateResult(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> quizOpt = quizDao.findById(id);
            if (!quizOpt.isPresent()) {
                System.out.println("Quiz not found with ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Quiz quiz = quizOpt.get();
            List<Question> questions = quiz.getQuestions();
            int right = 0;

            for (Response rep : responses) {
                // Find the question in the quiz by ID
                Question q = questions.stream()
                        .filter(ques -> ques.getId().equals(rep.getQuestionId()))
                        .findFirst()
                        .orElse(null);

                if (q != null) {
                    String submitted = rep.getResponse() != null ? rep.getResponse().trim() : "";
                    String correct = q.getCorrectAnswer() != null ? q.getCorrectAnswer().trim() : "";

                    System.out.println("Question ID: " + q.getId());
                    System.out.println("Submitted: '" + submitted + "' | Correct: '" + correct + "'");

                    // Compare answers ignoring case and leading/trailing spaces
                    if (submitted.equalsIgnoreCase(correct)) {
                        right++;
                        System.out.println("✅ Correct!");
                    } else {
                        System.out.println("❌ Wrong!");
                    }
                } else {
                    System.out.println("⚠️ No question found for submitted ID: " + rep.getQuestionId());
                }
            }

            System.out.println("Total Correct: " + right + " / " + questions.size());
            return new ResponseEntity<>(new Result(right, questions.size()), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
