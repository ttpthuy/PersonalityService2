package com.example.demo.dto;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class AnswerNewDTO {
    private List<AnswerDTO> answer;
    private List<ScoreDTO> score;

    public AnswerNewDTO(List<AnswerDTO> answer, List<ScoreDTO> score) {
        this.answer = answer;
        this.score = score;
    }

    @Override
    public String toString() {
        return "AnswerNewDTO{" +
                "answer=" + answer +
                ", score=" + score +
                '}' + "\n";
    }
}
