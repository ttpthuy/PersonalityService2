package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
@Getter @Setter @NoArgsConstructor
public class UserDTO {
    private  List<ScoreDTO> schoolScore;
    private List<AnswerDTO> listAnswerDTOS;


    public UserDTO(List<AnswerDTO> answerDTOS, List<ScoreDTO> scoreDTOS){
        this.listAnswerDTOS = answerDTOS;
        this.schoolScore = scoreDTOS;
    }
    @Override
    public String toString() {
        return "UserDTO{" +
                "schoolScore=" + schoolScore +
                ", listAnswerDTOS=" + listAnswerDTOS +
                '}' + "\n";
    }
}
