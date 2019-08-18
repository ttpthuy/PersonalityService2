package com.example.demo.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JobOfGroup {
	private String id;
	private String name;
	private String group;
	public JobOfGroup(String id, String name, String group) {
		super();
		this.id = id;
		this.name = name;
		this.group = group;
	}
	
	public JobOfGroup(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getString("Id_GroupQS");
		this.name = resultSet.getString("G_Name");
		this.group = resultSet.getString("Nhom");
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
