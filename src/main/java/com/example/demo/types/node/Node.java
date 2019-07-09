package com.example.demo.types.node;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Andre Godinez
 *
 * Abstract class to represent com.example.getSchoolScore.types.node.Node
 *
 */
public abstract class Node {

    public List<Node> children;
    public String getLk() {
		return lk;
	}

	public String lk;

    public void setLk(String lk) {
		this.lk = lk;
	}

	public Node() {
        children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void print(String prefix) {};
}
