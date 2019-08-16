package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AnswerDTO {
    private int ans;//diem
    private String idQs;

    public AnswerDTO(int ans, String idQs) {
        this.ans = ans;
        this.idQs = idQs;

    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "ans=" + ans +
                ", idQs='" + idQs + '\'' +
                '}';
    }
}
