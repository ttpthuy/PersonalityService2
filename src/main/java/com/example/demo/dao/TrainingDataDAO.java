package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.demo.types.Attribute;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrainingDataDAO {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ArrayList<String> processInstanceList(List<Attribute> attributes) throws IOException {

//        ArrayList<Instance> instanceList = new ArrayList<>();
//        jdbcTemplate = new JdbcTemplate(dataSource);
//
//        String sql = "SELECT * FROM tuvannghenghiep.traning_data";
//        jdbcTemplate.query(sql, new ResultSetExtractor() {
//            @Override
//            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                while (rs.next()) {
//                    ArrayList<String> attributeValues = new ArrayList<>();
//                    attributeValues.add(rs.getString("Gioi_tinh"));
//                    attributeValues.add(rs.getString("Diem_TN"));
//                    attributeValues.add(rs.getString("Diem_XH"));
//                    attributeValues.add(rs.getString("Chenh_lech_nang_luc"));
//                    attributeValues.add(rs.getString("Diem_JohnHoland"));
//                    attributeValues.add(rs.getString("Diem_chuyen_sau"));
//                    LinkedHashMap<String, String> attributeValuePairs = new LinkedHashMap<>();
//                    for (int i = 0; i < attributeValues.size(); i++) {
//                        attributeValuePairs.put(attributes.get(i).getName(), attributeValues.get(i));
//                    }
//                    String targetValue = rs.getString("Ket_qua").trim();
//                    instanceList.add(new Instance(attributeValuePairs, targetValue));
//
//                }
//                return instanceList;
//            }
//        });
        return null;
    }
}
