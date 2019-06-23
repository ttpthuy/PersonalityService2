package com.example.demo.main;

import com.example.demo.dao.DataQsDAO;
import com.example.demo.model.TrainingData;
import com.example.demo.types.Attribute;
import com.example.demo.types.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class DAO {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public @ResponseBody  List<Instance> getQuestions(List<Attribute> attributes){
            ArrayList<Instance> instanceList = new ArrayList<>();
            String sql = "SELECT * FROM tuvannghenghiep.traning_data;";
            jdbcTemplate = new JdbcTemplate(dataSource);
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
            return instanceList;
    }
    public List<TrainingData> getTraining(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM tuvannghenghiep.traning_data;";
        List<TrainingData> data = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor() {
            @Override
            public List<TrainingData> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while (resultSet.next()){
                    TrainingData trainingData = new TrainingData(resultSet);
                    data.add(trainingData);
                }
                return data;
            }
        });
        System.out.println(data);
        return data;
    }

    public List<Question> getQuestions2(){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM Question;";
        List<Question> list = new ArrayList<>();
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
        System.out.println(list);
        return list;
    }
}
