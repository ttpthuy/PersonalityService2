package com.example.demo.dto;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.main.Question;


public class UserDTO {
	private String sex;
	private List<ScoreDTO> schoolScore;
	private List<AnswerDTO> listAnswerDTOS;
	private List<Question> listQs;
	private List<Job> listJob;

	public UserDTO() {
	}

	public List<Job> showTop3(String taget){
		List<Job> lTop = new ArrayList<Job>();
		double avgtmp= 0;
		if("tn".equals(taget)) {
			avgtmp = (avgScoreTN()+avgJohnHolland())/2;
		}
		if("xh".equals(taget)) {
			avgtmp = (avgScoreXH()+avgJohnHolland())/2;
		}
		
			for (int j = 0; j < listQs.size(); j++) {
				for (int k = 0; k < listAnswerDTOS.size(); k++) {
					if(listQs.get(j).getIdQs().equals(listAnswerDTOS.get(k).getIdQs())) {
						for (int i = 0; i < listJob.size(); i++) {
							if(listJob.get(i).getId().equals(listQs.get(j).getIdGrQs())){
								listJob.get(i).setScore(listAnswerDTOS.get(k).getAns());
							}
							
						}
					}
				}
		}
			
			
			for (int i = 0; i < listJob.size(); i++) {
				listJob.get(i).setScore((listJob.get(i).getScore()*2+avgtmp)/3);
			}
			lTop= listJob.stream()
					.sorted(Comparator.comparing( Job :: getScore).reversed())
					.collect(Collectors.toList());
			while(lTop.size()>3) {
				lTop.remove(lTop.size()-1);
			}
		return lTop;
	}

	public double avgJohnHolland() {
		int count=0;
		int sum =0;
		for (int i = 0; i < listAnswerDTOS.size(); i++) {
			if(listAnswerDTOS.get(i).getIdQs().startsWith("JH")) {
				sum+= listAnswerDTOS.get(i).getAns();
				count++;
			}
		}
		return sum/count;
	}
	
	public double avgChuyenSau() {
		int count=0;
		int sum =0;
		for (int i = 0; i < listAnswerDTOS.size(); i++) {
			if(!listAnswerDTOS.get(i).getIdQs().startsWith("JH")) {
				sum+= listAnswerDTOS.get(i).getAns();
				count++;
			}
		}
		return sum/count;
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
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<Job> getListJob() {
		return listJob;
	}

	public void setListJob(List<Job> listJob) {
		this.listJob = listJob;
	}

}
