package com.example.demo.types;

import java.util.LinkedHashMap;

/**
 * @Author Andre Godinez
 *
 * com.example.demo.types.Instance class
 *
 */
public class Instance {

    // the target value / label for this instance
    private String targetValue;

    // the list of attribute value pairs
    private LinkedHashMap<String, String> attributeValues;

    /**
     * com.example.demo.types.Instance constructor
     *
     * @param attributeValues
     * @param targetValue
     */
    public Instance(LinkedHashMap<String, String> attributeValues, String targetValue) {
        this.targetValue = targetValue;
        this.attributeValues = attributeValues;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public LinkedHashMap<String, String> getAttributeValues() {
        return attributeValues;
    }

    @Override
    public String toString() {
        return "com.example.demo.types.Instance{" +
                "targetValue='" + targetValue + '\'' +
                ", attributeValues=" + attributeValues +
                '}' + "\n";
    }
}
