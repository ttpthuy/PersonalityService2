package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoreDTO {
	private double anh;
	private double hoa;
	private String idLevel;
	private double ly;
	private double toan;
	private double van;
	private double sinh;
	private double su;
	private double dia;

	public ScoreDTO(double anh, double hoa, String idLevel, double ly, double toan, double van, double sinh, double su,
			double dia) {
		this.anh = anh;
		this.hoa = hoa;
		this.idLevel = idLevel;
		this.ly = ly;
		this.toan = toan;
		this.van = van;
		this.sinh = sinh;
		this.su = su;
		this.dia = dia;
	}

	public double getDiemTN() {
		return (toan + ly + hoa + sinh + anh) / 5;
	}

	public double getDiemXH() {
		return (van + su + dia + toan + anh) / 5;
	}

	@Override
	public String toString() {
		return "ScoreDTO{" + "idLevel='" + idLevel + '\'' + ", toan=" + toan + ", van=" + van + ", anh=" + anh + ", ly="
				+ ly + ", hoa=" + hoa + "sinh=" + sinh + "su=" + su + "dia=" + dia + '}' + "/n";
	}
}
