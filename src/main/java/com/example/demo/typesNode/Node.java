package com.example.demo.typesNode;

import java.util.ArrayList;
import java.util.List;


public abstract class Node {

    public List<Node> children;
    public String lk;
    
	public Node() {
        children = new ArrayList<>();
    }
	public String getLk() {
		return lk;
	}
	 public void setLk(String lk) {
			this.lk = lk;
		}

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void print(String prefix) {};
}
