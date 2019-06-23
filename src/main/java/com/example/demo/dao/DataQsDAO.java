package com.example.demo.dao;

import com.example.demo.main.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.example.demo.types.Attribute;
import com.example.demo.types.Instance;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@RestController
public class DataQsDAO {
	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;
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
	public ArrayList<Instance> processInstanceList(List<Attribute> attributes) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
		try {
			ArrayList<Instance> instanceList = new ArrayList<>();
			jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT * FROM tuvannghenghiep.traning_data";
			jdbcTemplate.query(sql, new ResultSetExtractor() {
				@Override
				public List<Instance> extractData(ResultSet rs) throws SQLException, DataAccessException {
					while (rs.next()) {
						ArrayList<String> attributeValues = new ArrayList<>();
						attributeValues.add(rs.getString("Gioi_tinh"));
						attributeValues.add(rs.getString("Diem_TN"));
						attributeValues.add(rs.getString("Diem_XH"));
						attributeValues.add(rs.getString("Chenh_lech_nang_luc"));
						attributeValues.add(rs.getString("Diem_JohnHoland"));
						attributeValues.add(rs.getString("Diem_chuyen_sau"));
						LinkedHashMap<String, String> attributeValuePairs = new LinkedHashMap<>();
						for (int i = 0; i < attributeValues.size(); i++) {
							attributeValuePairs.put(attributes.get(i).getName(), attributeValues.get(i));
						}
						String targetValue = rs.getString("Ket_qua").trim();
						instanceList.add(new Instance(attributeValuePairs, targetValue));

					}
					return instanceList;
				}
			});

			System.out.println(instanceList);
		}catch (Exception e){}
		return null;
	}

	@GetMapping("/xyz" )
	public List<Question> getQuestions(){
		System.out.println("connect hello");
		List<Question> list = new ArrayList<>();
		String sql = "SELECT * FROM Question;";
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			@Override
			public List<Question> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				while(resultSet.next()){
					Question q = new Question(resultSet);
					list.add(q);
				}
				return list;
			}
		});
//        Map<Integer, Question> mapQuestion = new HashMap<>();
//        for(int i = 0; i < list.size(); i++){
//            mapQuestion.put(i, list.get(i));
//        }
//        List<Map.Entry<Integer, Question>> listMap = new ArrayList<Map.Entry<Integer, Question>>(mapQuestion.entrySet());
//
//        Collections.shuffle(listMap);
//        for (Map.Entry<Integer, Question> entry : listMap) {
//            System.out.println(entry.getKey() + " :: " + entry.getValue());
//        }
		return list;
	}




}
