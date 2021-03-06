package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter @Setter
public class Question {  
	private String idQs;
    private String idGrQs;
    private String qus;
//    private int ans;

    public Question(String idQs, String idGrQs, String qus) {
        this.idQs = idQs;
        this.idGrQs = idGrQs;
        this.qus = qus;
    }

  

	public Question(ResultSet resultSet) throws SQLException {
        this.idQs = resultSet.getString("Id_Question");
        this.idGrQs = resultSet.getString("Id_GroupQS");
        this.qus = resultSet.getString("text_QS");
    }
	public String getIdQs() {
		return idQs;
	}



	public void setIdQs(String idQs) {
		this.idQs = idQs;
	}



	public String getIdGrQs() {
		return idGrQs;
	}



	public void setIdGrQs(String idGrQs) {
		this.idGrQs = idGrQs;
	}



	public String getQus() {
		return qus;
	}



	public void setQus(String qus) {
		this.qus = qus;
	}
	




	@Override
    public String toString() {
        return "Question{" +
                "idQs='" + idQs + '\'' +
                ", idGrQs='" + idGrQs + '\'' +
                ", qus='" + qus + '\'' +
                '}' + "\n";
    }
}
