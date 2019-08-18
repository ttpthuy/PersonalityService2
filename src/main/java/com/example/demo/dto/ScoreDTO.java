package com.example.demo.dto;



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
	
	public ScoreDTO(String idlever,double toan, double ly, double hoa, double sinh , double anh) {
		this.toan =toan;
		this.ly = ly;
		this.hoa = hoa;
		this.sinh = sinh;
		this.anh = anh;
		this.idLevel =idlever;
	}

	
	public ScoreDTO(double anh, String idLevel, double toan, double van, double su, double dia) {
		this.anh = anh;
		this.idLevel = idLevel;
		this.toan = toan;
		this.van = van;
		this.su = su;
		this.dia = dia;
	}

	public double getDiemTN() {
		return (toan + ly + hoa + sinh + anh) / 5;
	}

	public double getDiemXH() {
		return (van + su + dia + toan + anh) / 5;
	}
	
	

	public double getAnh() {
		return anh;
	}

	public void setAnh(double anh) {
		this.anh = anh;
	}

	public double getHoa() {
		return hoa;
	}

	public void setHoa(double hoa) {
		this.hoa = hoa;
	}

	public String getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(String idLevel) {
		this.idLevel = idLevel;
	}

	public double getLy() {
		return ly;
	}

	public void setLy(double ly) {
		this.ly = ly;
	}

	public double getToan() {
		return toan;
	}

	public void setToan(double toan) {
		this.toan = toan;
	}

	public double getVan() {
		return van;
	}

	public void setVan(double van) {
		this.van = van;
	}

	public double getSinh() {
		return sinh;
	}

	public void setSinh(double sinh) {
		this.sinh = sinh;
	}

	public double getSu() {
		return su;
	}

	public void setSu(double su) {
		this.su = su;
	}

	public double getDia() {
		return dia;
	}

	public void setDia(double dia) {
		this.dia = dia;
	}

	@Override
	public String toString() {
		return "ScoreDTO{" + "idLevel='" + idLevel + '\'' + ", toan=" + toan + ", van=" + van + ", anh=" + anh + ", ly="
				+ ly + ", hoa=" + hoa + "sinh=" + sinh + "su=" + su + "dia=" + dia + '}' + "/n";
	}
}
