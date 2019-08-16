package com.example.demo.dto;



import java.util.List;

import com.example.demo.main.Question;


public class UserDTO {
	private String sex;
	private List<ScoreDTO> schoolScore;
	private List<AnswerDTO> listAnswerDTOS;
	private List<Question> listQs;
	private List<Job> listJob;

	public UserDTO() {
	}

	public UserDTO(List<AnswerDTO> answerDTOS, List<ScoreDTO> scoreDTOS) {
		this.listAnswerDTOS = answerDTOS;
		this.schoolScore = scoreDTOS;
	}

	public List<ScoreDTO> getSchoolScore() {
		return schoolScore;
	}

	public void setSchoolScore(List<ScoreDTO> schoolScore) {
		this.schoolScore = schoolScore;
	}

	public List<AnswerDTO> getListAnswerDTOS() {
		return listAnswerDTOS;
	}

	public void setListAnswerDTOS(List<AnswerDTO> listAnswerDTOS) {
		this.listAnswerDTOS = listAnswerDTOS;
	}
	
	public List<Question> getListQs() {
		return listQs;
	}

	public void setListQs(List<Question> listQs) {
		this.listQs = listQs;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	

	public boolean isKhoiTN() {
		double diemTN = 0;
		for (int i = 0; i < schoolScore.size(); i++) {
			diemTN += schoolScore.get(i).getDiemTN();
		}
		diemTN /= schoolScore.size();

		double diemXH = 0;
		for (int i = 0; i < schoolScore.size(); i++) {
			diemXH += schoolScore.get(i).getDiemXH();
		}
		diemXH /= schoolScore.size();
		return (diemTN - diemXH) > -0.1 ? true : false;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "schoolScore=" + schoolScore + ", listAnswerDTOS=" + listAnswerDTOS + '}' + "\n";
	}
}
