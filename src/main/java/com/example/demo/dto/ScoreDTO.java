package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ScoreDTO {
    private double anh;
    private double hoa;
    private String idLevel;
    private double ly;
    private double toan;
    private double van;

    public ScoreDTO(String idLevel, double toan, double van, double anh, double ly, double hoa) {
        this.idLevel = idLevel;
        this.toan = toan;
        this.van = van;
        this.anh = anh;
        this.ly = ly;
        this.hoa = hoa;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "idLevel='" + idLevel + '\'' +
                ", toan=" + toan +
                ", van=" + van +
                ", anh=" + anh +
                ", ly=" + ly +
                ", hoa=" + hoa +
                '}' + "/n";
    }
}
