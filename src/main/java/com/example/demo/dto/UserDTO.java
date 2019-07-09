package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
@Getter @Setter @NoArgsConstructor
public class UserDTO {
    private  HashMap<String, HashMap<String, String>> schoolScore;
    private List<AnswerDTO> listAnswerDTOS;

    public UserDTO(HashMap<String, HashMap<String, String>> schoolScore, List<AnswerDTO> listAnswerDTOS) {
        this.schoolScore = schoolScore;
        this.listAnswerDTOS = listAnswerDTOS;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "schoolScore=" + schoolScore +
                ", listAnswerDTOS=" + listAnswerDTOS +
                '}' + "\n";
    }
}
