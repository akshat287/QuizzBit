package com.techieakshattt.QuizzBit.dao;

import com.techieakshattt.QuizzBit.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
