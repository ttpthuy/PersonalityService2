package com.example.demo.classifier;

import com.example.demo.data.Data;
import com.example.demo.types.Attribute;
import com.example.demo.types.Instance;
import com.example.demo.types.node.ContinuousNode;
import com.example.demo.types.node.DiscreteNode;
import com.example.demo.types.node.LeafNode;
import com.example.demo.types.node.Node;
import com.example.demo.util.C45Util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

public class C45 {

	// global values for targetAttribute and possible values
	public Attribute targetAttribute;
	public List<String> possibleTargetValues;

	public Node decisionTree;

	// minimum instances a decide a node
	public int minimumInstances;

	// maximum depth of decision tree
	public int maxDepth;

	/**
	 * Default constructor
	 */
	public C45() {
		this.minimumInstances = 10;
		this.maxDepth = 0;
	}

	public C45(int minimumInstances, int maxDepth) {
		this.minimumInstances = minimumInstances;
		this.maxDepth = maxDepth;
	}

	public void printDecisionTree() {
		this.decisionTree.print("");
	}

	public Node getDecisionTree() {
		return decisionTree;
	}

	/**
	 * @Author Andre Godinez
	 *
	 *         Training function given com.example.demo.data of instances and attributes
	 *
	 * @param data
	 *            - the com.example.demo.data object containing the attributes and instances
	 *
	 */
	public void train(Data data) {
		targetAttribute = data.getTargetAttribute();
		possibleTargetValues = targetAttribute.getPossibleValues();

		C45Util.possibleTargetValues = possibleTargetValues;

		this.decisionTree = fit(data.getInstanceList(), data.getAttributes(), data.getInstanceList(), 0);
	}

	/**
	 * @Author Cillian Fennell kiem tra do chinh xac cua thuat toan Testing
	 *         function that returns the accuracy of c45 decision tree.
	 *
	 * @param instanceList
	 *            - the list of instances containing test com.example.demo.data
	 * @param node
	 *            - build decision tree
	 * @return accuracy
	 *
	 */
	public double accuracy(List<Instance> instanceList, Node node) {

		// Count of instances whose actual targetValue is equal to predicted
		long count = instanceList.stream().filter(x -> x.getTargetValue().equals(predict(x, node))).count();

		double accuracy = (double) count / instanceList.size();
		return accuracy;
	}

	/**
	 * @Author Cillian Fennell
	 *
	 *         Prints out actual vs expected targetValues to txt file, marks
	 *         incorrect classifications with an 'X'
	 *
	 * @param instanceList
	 * @param node
	 *            - built decision tree
	 * @param iteration
	 * @param out
	 *
	 */
	public void outputActualPredicted(List<Instance> instanceList, Node node, int iteration, PrintWriter out)
			throws FileNotFoundException, UnsupportedEncodingException {
		out.println("=========================== Iteration " + (iteration + 1) + "============================");
		out.println(String.format("%-20s %-20s", "Actual", "Predicted"));

		String actual;
		String predicted;

		for (Instance instance : instanceList) {
			actual = instance.getTargetValue();
			predicted = predict(instance, node);
//			System.out.println(instance.toString());

			if (actual.equals(predicted)) {
				out.println(String.format("%-20s %-20s", actual, predicted));
			} else {
				out.println(String.format("%-20s %-20s             X", actual, predicted));
			}

		}
	}

	/**
	 * @Author Cillian Fennell
	 *
	 *         Testing function shuffles the instance list and splits it into
	 *         test and training datasets.
	 *
	 * @param data
	 *            - input com.example.demo.data from file
	 * @param n
	 *            - num times to do cross validation
	 * @return HashMap of list of accuracies and average accuracy
	 *
	 */
	public void crossValidation(Data data, int n) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter out = new PrintWriter("actual_predicted.txt", "UTF-8");

		// Fetch instanceList from input com.example.demo.data
		List<Instance> instanceList = data.getInstanceList();
		List<Attribute> attributeList = data.getAttributes();

		int first = (int) Math.ceil(instanceList.size() * 0.33);
		int second = (int) Math.ceil(instanceList.size() * 0.66);
		int third = instanceList.size();

		ArrayList<Double> accuracies = new ArrayList<>();
		double accuracy;

		for (int i = 0; i < n; i++) {
			// Shuffle list
			Collections.shuffle(instanceList);

			// Splitting instances into three sub lists
			List<Instance> one = instanceList.subList(0, first);
			List<Instance> two = instanceList.subList(first, second);
			List<Instance> three = instanceList.subList(second, third);

			List<Instance> training = new ArrayList<>();
			List<Instance> test = new ArrayList<>();

			int j = n % 3;

			switch (j) {
			case 0:
				test = one;
				training.addAll(two);
				training.addAll(three);
				break;
			case 1:
				training.addAll(one);
				test = two;
				training.addAll(three);
				break;
			case 2:
				training.addAll(one);
				training.addAll(two);
				test = three;
				break;
			}

			// Use training dataset to fit the model
			Node node = fit(training, attributeList, training, 0);
			System.out.println("---------------");
//			node.print("");
			
//			System.out.println("--training set--");
//			for (Instance instance : training) {
//				System.out.println(instance.toString());
//			}
//			System.out.println("--end training set--");
			
			accuracy = 100.0 * accuracy(test, node);
			outputActualPredicted(test, node, i, out);

			System.out.println("Iteration " + (i + 1) + ": " + Math.round(accuracy) + "%");
			accuracies.add(accuracy);
		}

		Double averageAccuracy = accuracies.stream().mapToDouble(val -> val).average().orElse(0.0);
		System.out.println("Average accuracy in " + n + " iterations: " + Math.round(averageAccuracy) + "%");

		out.flush();
		out.close();
	}

	/**
	 * @Author Cillian Fennell
	 *
	 *         NB - Only works if the decision tree is built dua ra targetValue
	 *         du doan Predicts the targetValue given the instance and the
	 *         decisionTree
	 *
	 * @param instance
	 * @param node
	 * @return the predicted targetValue
	 *
	 */
	public String predict(Instance instance, Node node) {
		// Return targetValue if current node is a leaf node
		if (node instanceof LeafNode) {
			return ((LeafNode) node).targetValue;
		}

		
		
		//continuous node
		// Extract attribute value as double
		try {
			double instanceValue = Double.parseDouble(instance.getAttributeValues().get(((ContinuousNode) node).getName()));
			
			// Recursively call method until current node is leaf node
			if (instanceValue <= ((ContinuousNode) node).getThreshold()) {
				return predict(instance, node.children.get(0));
			} else {
				return predict(instance, node.children.get(1));
			}
		} catch (Exception e) {
			// discreteNode
			String instanceValue = instance.getAttributeValues().get(((DiscreteNode) node).getName());
			
			 for (int i = 0; i <((DiscreteNode) node).getValue().size(); i++) {
			if(instanceValue.equals(((DiscreteNode) node).getValue().get(i))){
				 for(Node child:  node.children) {
						
			            if(child.getLk().equals(((DiscreteNode) node).getValue().get(i)))
			            	return predict(instance, child);
				 }
			}
		}
			 return null;
		}
		
	}

	/**
	 * @Author Andre Godinez
	 *
	 *         Creates a decision tree with child nodes given an instanceList
	 *         and attributeList
	 *
	 * @param instanceList
	 * @param attributeList
	 * @param parentInstances
	 * @return com.example.demo.types.node.Node - built Decision Tree
	 *
	 */
	public Node fit(List<Instance> instanceList, List<Attribute> attributeList, List<Instance> parentInstances,
			int depth) {

		// base cases
		if (instanceList.isEmpty()) {
			return new LeafNode(C45Util.majorityTarget(parentInstances));
		}

		if (instanceList.size() < minimumInstances) {
			return new LeafNode(C45Util.majorityTarget(instanceList));
		}

		if (depth == maxDepth && maxDepth != 0) {
			return new LeafNode(C45Util.majorityTarget(instanceList));
		}

		if (C45Util.unanimousTarget(instanceList)) {
			return new LeafNode(instanceList.get(0).getTargetValue());
		}

		// get the best attribute - attribute with most gain
		Attribute bestAttribute = C45Util.bestAttribute(instanceList, attributeList);

		if (bestAttribute.equals(0.0)) {
			return new LeafNode(C45Util.majorityTarget(instanceList));
		}

		if (bestAttribute.getPossibleValues().get(0).equals("text")) {
			Node root = new DiscreteNode(bestAttribute.getName());
			HashMap<String, List<Instance>> subsets = C45Util.subsetInstanceListDiscrete(bestAttribute, instanceList);
			for (Map.Entry<String, List<Instance>> entry : subsets.entrySet()) {
				String s = entry.getKey();
				((DiscreteNode) root).addValue(s);
				List<Attribute> remainingAttributes = attributeList.stream()
						.filter(x -> !x.getName().equals(bestAttribute.getName())).collect(Collectors.toList());
				List<Instance> subsetList = entry.getValue();

				Node child = fit(subsetList, remainingAttributes, instanceList, depth + 1);
				child.setLk(entry.getKey());
				root.addChild(child);
			}

			return root;
		} else {

			// get related best threshold with this
			double currentThreshold = C45Util.bestThreshold;
			Node root = new ContinuousNode(bestAttribute.getName());
			((ContinuousNode) root).setThreshold(currentThreshold);

			HashMap<String, List<Instance>> subsets = C45Util.subsetInstanceListContinuous(bestAttribute, instanceList,
					currentThreshold);

			for (Map.Entry<String, List<Instance>> entry : subsets.entrySet()) {
				// List<Attribute> remainingAttributes = attributeList;
				List<Attribute> remainingAttributes = attributeList.stream()
						.filter(x -> !x.getName().equals(bestAttribute.getName())).collect(Collectors.toList());

				List<Instance> subsetList = entry.getValue();

				Node child = fit(subsetList, remainingAttributes, instanceList, depth + 1);
				root.addChild(child);
			}

			return root;
		}
	}

	public static void main(String[] args) throws IOException {
		// String[] attributes = {"body-length real n", "wing-length real n",
		// "body-width real n", "wing-width real n",
		// "type [BarnOwl,SnowyOwl,LongEaredOwl] target"};
//		String[] attributes = { "OUTLOOK text n", "TEMPERATURE real n", "HUMIDITY real n", "WINDY text n",
//				"type [Play,Don't_Play] target" };
		
//		String[] attributes = { "John_holland real n", "gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n",
//			"diem_trung_binh real n", "diem_cau_hoi_chuyen_sau real n",	"type [tn,xh,FALSE] target" };
		
		
//		String[] attributes = {"gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n","do_chenh_lech_nang_luc real n",
//				"diem_trung_binh real n", "John_holland(tu_nhien) real n",  "John_holland(xa_hoi) real n",
//				"do_chenh_lech_tinh_cach real n","chenh_lech_nang_luc_tinh_cach real n","type [tn,xh,FALSE] target" };
		
//		String[] attributes = {"gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n",
//				"diem_trung_binh real n", "John_holland real n",  "diem_chuyen_sau real n",	"type [tn,xh,FALSE] target" };
		
		String[] attributes = {"gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n", "do_chenh_lech real n",
				"John_holland real n","diem_chuyen_sau real n","type [tn,xh,FALSE] target" };
		
		
		// String[] attributes = {"OUTLOOK text n", "TEMPERATURE text n",
		// "HUMIDITY text n", "WINDY text n",
		// "type [Play,Don't_Play] target"};

//		String fileName = "owls.csv";
		List<Instance> instances = new ArrayList<>();

		Data data = new Data(attributes);

		C45 classifier = new C45(0, 5);

		classifier.train(data);
		classifier.printDecisionTree();
		// kiem tra do chinh xac cua thuat toan
		 classifier.accuracy(data.getInstanceList(),
		 classifier.getDecisionTree());

		// Testing with random instance value
//		LinkedHashMap<String, String> avp = new LinkedHashMap<>();
		//
		// avp.put("body-length", "3");
		// avp.put("wing-length", "6.1");
		// avp.put("body-width", "4.5");
		// avp.put("wing-width", "1");

//		avp.put("OUTLOOK", "sunny");
//		avp.put("TEMPERATURE", "60");
//		avp.put("HUMIDITY", "67");
//		avp.put("WINDY", "TRUE");
//		//
//		 Instance test = new Instance(avp, "Test");
//		//
//		 String predictedValue = com.example.demo.classifier.predict(test,
//		 com.example.demo.classifier.getDecisionTree());
//		//
//		 System.out.println(predictedValue);
//
		 classifier.crossValidation(data, 10);
	}
}
