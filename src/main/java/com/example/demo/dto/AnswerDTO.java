package com.example.demo.dto;

public class AnswerDTO {
 

	private int ans;//diem
    private String idQs;

    public AnswerDTO(int ans, String idQs) {
        this.ans = ans;
        this.idQs = idQs;

    }
    public int getAns() {
 		return ans;
 	}

 	public void setAns(int ans) {
 		this.ans = ans;
 	}

 	public String getIdQs() {
 		return idQs;
 	}

 	public void setIdQs(String idQs) {
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
