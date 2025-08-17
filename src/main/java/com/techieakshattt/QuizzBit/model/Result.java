package com.techieakshattt.QuizzBit.model;


import lombok.Data;

@Data
public class Result {
    int total;
    int correct;

    public Result(int correct, int total){
        this.correct=correct;
        this.total=total;
    }
}
