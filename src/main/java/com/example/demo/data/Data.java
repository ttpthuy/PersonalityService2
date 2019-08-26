package com.example.demo.data;

import com.example.demo.types.Attribute;
import com.example.demo.types.Instance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {

	private List<Instance> instanceList;
	private List<Attribute> attributes;
	public Data(String[] attributesArray, List<Instance> instances) throws IOException {
		this.attributes = processAttributes(attributesArray);
//		this.instanceList = processInstanceList(fileName);
		this.instanceList = instances;
	}
	public Data(String[] attributesArray) throws IOException {
		this.attributes = processAttributes(attributesArray);
//		this.instanceList = processInstanceList(fileName);
		this.instanceList = new ArrayList<>();
	}

	public List<Instance> getInstanceList() {
		return instanceList;
	}

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    public List<Attribute> getAttributes() {
		return attributes.subList(0, attributes.size() - 1);
	}

	public Attribute getTargetAttribute() {
		return attributes.get(attributes.size() - 1);
	}

	/**
	 *
	 *         Process instanceList from a file, attributes should be populated
	 *         before running this.
	 */
//	private ArrayList<Instance> processInstanceList(String fileName) throws IOException {
//		Scanner scanner = new Scanner(new File(fileName));
//		ArrayList<Instance> instanceList = new ArrayList<>();
//
//		while (scanner.hasNextLine()) {
//			String line = scanner.nextLine();
//
//			ArrayList<String> attributeValues = new ArrayList<>(Arrays.asList(line.split(",")));
//
//			// Target value/label should be the last value in the columns
//			String targetValue = attributeValues.get(attributeValues.size() - 1);
//
//			LinkedHashMap<String, String> attributeValuePairs = new LinkedHashMap<>();
//
//			for (int i = 0; i < attributeValues.size() - 1; i++) {
//				attributeValuePairs.put(this.attributes.get(i).getName(), attributeValues.get(i));
//			}
//
//			instanceList.add(new Instance(attributeValuePairs, targetValue));
//		}
//		scanner.close();
//		return instanceList;
//	}

	/**
	 *
	 *         Process the attributes from a string array
	 *
	 */
	private ArrayList<Attribute> processAttributes(String[] attributesArray) throws IOException {

		ArrayList<Attribute> attributes = new ArrayList<>();

		// add all attributes to arraylist of attributes
		for (int i = 0; i < attributesArray.length; i++) {

			// split the string by all white space i.e. Length real becomes
			// ["Length",
			// "Real", "Target"].
			String[] attribute_value = attributesArray[i].split(" ");

			// if attribute_value length is not equal to 2 the format is wrong.
			if (attribute_value.length != 3)
				throw new IOException("Invalid attribute format");

			// name
			String name = attribute_value[0];

			// possible values
			List<String> possibleValues = new ArrayList<String>();
			if (attribute_value[1].equals("real")) {
				possibleValues.add("real");
			} //chinh sua
			else if (attribute_value[1].equals("text")) {
				possibleValues.add("text");
				//ket thuc chinh sua
			} else {
				String[] possibleValuesArr = attribute_value[1].substring(1, attribute_value[1].length() - 1)
						.split(",");
				for (String possibleValue : possibleValuesArr) {
					possibleValues.add(possibleValue);
				}
			}

			// is target
			boolean isTarget = attribute_value[2].equals("target") ? true : false;

			// create a new attribute object and add to the attribute set
			Attribute attr = new Attribute(name, possibleValues, isTarget);

			attributes.add(attr);
		}

		return attributes;
	}

	@Override
	public String toString() {
		return  "instanceList=" + instanceList + ", attributes=" + attributes + "\n";
	}

}
