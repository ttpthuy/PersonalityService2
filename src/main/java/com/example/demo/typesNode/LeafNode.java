package com.example.demo.typesNode;


public class LeafNode extends Node {

    public String targetValue;

    public LeafNode(String targetValue) {
        this.targetValue = targetValue;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix  + "|__ " + targetValue);
    };
}
