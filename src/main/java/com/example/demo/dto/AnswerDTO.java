package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AnswerDTO {
    private int ans;
    private String idQs;
    private int position;

    public AnswerDTO(int ans, String idQs, int position) {
        this.ans = ans;
        this.idQs = idQs;
        this.position = position;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "ans=" + ans +
                ", idQs='" + idQs + '\'' +
                ", position=" + position +
                '}';
    }
}
