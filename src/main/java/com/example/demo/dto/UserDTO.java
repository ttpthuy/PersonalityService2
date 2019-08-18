package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.main.Question;

public class UserDTO {
	private byte sex;
	private List<ScoreDTO> schoolScore;
	private List<AnswerDTO> listAnswerDTOS;
	private List<Question> listQs;
	private List<Job> listJob;
	private Job jobTest;

	public UserDTO() {
	}

	public Job showResult(String taget) {
		double avgtmp = 0;
		if ("TRUE".equals(taget)) {
			if (avgScoreTN() > 1) {
				avgtmp = (avgScoreTN() + avgAnswer()) / 2;
			} else {
				avgtmp = (avgScoreXH() + avgAnswer()) / 2;
			}
			if (this.jobTest.isCoincidence(this.sex)) {
				avgtmp = avgtmp + (Math.abs(this.jobTest.getMaleRatio() - 0.5) + this.jobTest.getJobMarket() * 0.1);

			} else {
				avgtmp = avgtmp - (Math.abs(this.jobTest.getMaleRatio() - 0.5) + this.jobTest.getJobMarket() * 0.1);
			}
			this.jobTest.setScore(avgtmp);
		} else {
			if (avgScoreTN() > 1) {
				avgtmp = (avgScoreTN() + avgAnswer()) / 2;
			} else {
				avgtmp = (avgScoreXH() + avgAnswer()) / 2;
			}
			if (this.jobTest.isCoincidence(this.sex)) {
				avgtmp = avgtmp + (Math.abs(this.jobTest.getMaleRatio() - 0.5) + this.jobTest.getJobMarket() * 0.1);

			} else {
				avgtmp = avgtmp - (Math.abs(this.jobTest.getMaleRatio() - 0.5) + this.jobTest.getJobMarket() * 0.1);
			}
			this.jobTest.setScore(-avgtmp);
		}

		return this.jobTest;
	}

	public List<Job> showTop3(String taget) {
		List<Job> lTop = new ArrayList<Job>();
		if("FALSE".equals(taget)) {
			return lTop;
		}
		double avgtmp = 0;
		if ("tn".equals(taget)) {
			avgtmp = (avgScoreTN() + avgJohnHolland()) / 2;
		}
		if ("xh".equals(taget)) {
			avgtmp = (avgScoreXH() + avgJohnHolland()) / 2;
		}

		for (int j = 0; j < listQs.size(); j++) {
			for (int k = 0; k < listAnswerDTOS.size(); k++) {
				if (listQs.get(j).getIdQs().equals(listAnswerDTOS.get(k).getIdQs())) {
					for (int i = 0; i < listJob.size(); i++) {
						if (listJob.get(i).getId().equals(listQs.get(j).getIdGrQs())) {
							if (listJob.get(i).getScore() == 0) {
								listJob.get(i).setScore(listAnswerDTOS.get(k).getAns());
							} else {
								listJob.get(i)
										.setScore((listJob.get(i).getScore() + listAnswerDTOS.get(k).getAns()) / 2);
							}
						}

					}
				}
			}
		}

		for (int i = 0; i < listJob.size(); i++) {
			listJob.get(i).setScore(((listJob.get(i).getScore()/5*10) * 2 + avgtmp) / 3);
			if (this.sex == 0) {
				if (listJob.get(i).getMaleRatio() >= 0.5) {
					listJob.get(i).setScore(listJob.get(i).getScore() + listJob.get(i).getMaleRatio() - 0.5
							+ listJob.get(i).getJobMarket() * 0.1);
				} else {
					listJob.get(i).setScore(listJob.get(i).getScore() + 0.5 - listJob.get(i).getMaleRatio()
							+ listJob.get(i).getJobMarket() * 0.1);
				}
			}
		}
		lTop = listJob.stream().sorted(Comparator.comparing(Job::getScore).reversed()).collect(Collectors.toList());
		while (lTop.size() > 3) {
			lTop.remove(lTop.size() - 1);
		}
		return lTop;
	}

	public double avgAnswer() {
		double sum = 0;
		for (int i = 0; i < listAnswerDTOS.size(); i++) {
			sum += listAnswerDTOS.get(i).getAns();
		}
		return (sum / listAnswerDTOS.size()/5*10);
	}

	public double avgJohnHolland() {
		double count = 0;
		double sum = 0;
		for (int i = 0; i < listQs.size(); i++) {
			for (int j = 0; j < listAnswerDTOS.size(); j++) {
				if (listQs.get(i).getIdQs().equals(listAnswerDTOS.get(j).getIdQs()) && listQs.get(i).getIdGrQs().startsWith("JH")) {
					sum += listAnswerDTOS.get(j).getAns();
					count++;
				}
			}
		}
		System.out.println("sum jh "+sum/count);
		return ((sum / count)/5)*10;
	}

	public double avgChuyenSau() {
		double count = 0;
		double sum = 0;
		for (int i = 0; i < listQs.size(); i++) {
			for (int j = 0; j < listAnswerDTOS.size(); j++) {
				if (listQs.get(i).getIdQs().equals(listAnswerDTOS.get(j).getIdQs()) && !listQs.get(i).getIdGrQs().startsWith("JH")) {
					sum += listAnswerDTOS.get(j).getAns();
					count++;
				}
			}
		}
		System.out.println("sum cs "+sum/count);
		return ((sum / count)/5)*10;
	}

	public boolean isKhoiTN() {
		return (avgScoreTN() - avgScoreXH()) > -0.1 ? true : false;
	}

	public double avgScoreTN() {
		double diemTN = 0;
		for (int i = 0; i < schoolScore.size(); i++) {
			diemTN += schoolScore.get(i).getDiemTN();
		}
		diemTN /= schoolScore.size();
		return diemTN;
	}

	public double avgScoreXH() {
		double diemXH = 0;
		for (int i = 0; i < schoolScore.size(); i++) {
			diemXH += schoolScore.get(i).getDiemXH();
		}
		diemXH /= schoolScore.size();
		return diemXH;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "schoolScore=" + schoolScore + ", listAnswerDTOS=" + listAnswerDTOS + '}' + "\n";
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

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public List<Job> getListJob() {
		return listJob;
	}

	public void setListJob(List<Job> listJob) {
		this.listJob = listJob;
	}

	public Job getJobTest() {
		return jobTest;
	}

	public void setJobTest(Job jobTest) {
		this.jobTest = jobTest;
	}

}
