package com.example.demo.typesNode;

import java.util.ArrayList;
import java.util.List;

public class DiscreteNode extends Node {
	private List<String> value = new ArrayList<>();
	String name;
	
	
	

	public DiscreteNode(String name) {
		 super();
        this.name = name;
	}

	public List<String> getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}
	public void addValue(String value) {
		this.value.add(value);
	}
	 @Override
	    public void print(String prefix) {
		 for (int i = 0; i < value.size(); i++) {
			 String name = this.name;
			 name += " = ";
			 name += value.get(i);
			 System.out.println(prefix + "|-- " + name);
			 for(Node child: children) {
				
				
		            
		            if(child.getLk().equals(value.get(i)))
		            child.print(prefix +  "|        ");
			 }
		}
		 
	 }
}
