package com.example.demo.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Job {
 private String id;
 private String name;
 private double maleRatio;
 private int jobMarket;
 private double score;

public Job(String id, String name, double maleRatio, int jobMarket) {
	super();
	this.id = id;
	this.name = name;
	this.maleRatio = maleRatio;
	this.jobMarket = jobMarket;
}

public Job(ResultSet resultSet) throws SQLException {
	this.id = resultSet.getString("Id_Job");
	this.name = resultSet.getString("Name");
	this.maleRatio = resultSet.getDouble("Male_Ratio");
	this.jobMarket = resultSet.getInt("Job_market");
}

public double getMaleRatio() {
	return maleRatio;
}

public int getJobMarket() {
	return jobMarket;
}

@Override
	public String toString() {
		return this.name +"          " + "do phu hop" + this.score;
	}

public double getScore() {
	return score;
}
public void setScore(double score) {
	this.score = score;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public static void main(String[] args) {
	List<Job> lTop = new ArrayList<Job>();
	System.out.println(lTop);
}
 
}
