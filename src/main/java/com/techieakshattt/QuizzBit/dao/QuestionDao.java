package com.techieakshattt.QuizzBit.dao;

import com.techieakshattt.QuizzBit.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "Select * from question q where q.category=:category ORDER BY RAND() LIMIT :num ",nativeQuery = true)
    List<Question> FindRandomQuestionByCategory(String category, int num);
}
