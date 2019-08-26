package com.example.demo.dao;

import com.example.demo.dto.Job;
import com.example.demo.dto.JobOfGroup;
import com.example.demo.dto.Question;
import com.example.demo.types.Attribute;
import com.example.demo.types.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
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

	@ResponseBody
    public   List<Instance> getTrainingData(List<Attribute> attributes){
            ArrayList<Instance> instanceList = new ArrayList<>();
            String sql = "SELECT * FROM tuvannghenghiep.training_data;";
            jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
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
//        System.out.println(instanceList);
            return instanceList;
    }
	
	public   List<Instance> getTrainingData2(List<Attribute> attributes){
        ArrayList<Instance> instanceList = new ArrayList<>();
        String sql = "SELECT * FROM tuvannghenghiep.training2;";
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
            @Override
            public List<Instance> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    ArrayList<String> attributeValues = new ArrayList<>();
                    attributeValues.add(rs.getString("Gioi_Tinh"));
                    attributeValues.add(rs.getString("Diem_so"));
                    attributeValues.add(rs.getString("Diem_JohnHoland"));
                    attributeValues.add(rs.getString("Diem_chuyen_sau"));
                    attributeValues.add(rs.getString("Nhu_cau"));
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
//    System.out.println(instanceList);
        return instanceList;
}
	
//    public List<TrainingData> getTraining(){
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        String sql = "SELECT * FROM tuvannghenghiep.training_data;";
//        List<TrainingData> data = new ArrayList<>();
//        jdbcTemplate.query(sql, new ResultSetExtractor() {
//            @Override
//            public List<TrainingData> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//                while (resultSet.next()){
//                    TrainingData trainingData = new TrainingData(resultSet);
//                    data.add(trainingData);
//                }
//                return data;
//            }
//        });
//        System.out.println(data);
//        return data;
//    }

    public List<Question> getQuestionsTN(){
        System.out.println("danh sach cau hoi tn");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select q.*\r\n" + 
        		"from question q join question_group g on q.Id_GroupQS = g.Id_GroupQS\r\n" + 
        		"where g.Nhom like 'TN%';";
        List<Question> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
            @Override
            public List<Question> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while(resultSet.next()){
                    Question q = new Question(resultSet);
                    list.add(q);
                }
                return list;
            }
        });
//        System.out.println(list);
        return list;
    }
    public List<Question> getQuestionsXH(){
        System.out.println("danh sach cau hoi xh");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select q.*\r\n" + 
        		"from question q join question_group g on q.Id_GroupQS = g.Id_GroupQS\r\n" + 
        		"where g.Nhom like '%XH%';";
        List<Question> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
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
    
    public List<Job> getJobTN(){
        System.out.println("danh sach nganh tn");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select j.*\r\n" + 
        		"from question_group g join job j on g.Id_GroupQS = j.Id_Job\r\n" + 
        		"where g.Nhom like '%TN%';";
        List<Job> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
            @Override
            public List<Job> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while(resultSet.next()){
                    Job q = new Job(resultSet);
                    list.add(q);
                }
                return list;
            }
        });
        System.out.println(list);
        return list;
    }
    
    public List<Job> getJobXH(){
        System.out.println("danh sach nganh xh");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select j.*\r\n" + 
        		"from question_group g join job j on g.Id_GroupQS = j.Id_Job\r\n" + 
        		"where g.Nhom like '%XH%';";
        List<Job> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
            @Override
            public List<Job> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while(resultSet.next()){
                    Job q = new Job(resultSet);
                    list.add(q);
                }
                return list;
            }
        });
        System.out.println(list);
        return list;
    }
    
    public List<Question> getQuestionsJH_TN(){
        System.out.println("danh sach cau hoi JH_TN");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select q.*\r\n" + 
        		"from question q join question_group g on q.Id_GroupQS = g.Id_GroupQS\r\n" + 
        		"where g.Nhom like '%TN%' and g.Id_GroupQS like '%JH%';";
        List<Question> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
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
    
    public List<Question> getQuestionsJH_XH(){
        System.out.println("danh sach cau hoi JH_TN");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select q.*\r\n" + 
        		"from question q join question_group g on q.Id_GroupQS = g.Id_GroupQS\r\n" + 
        		"where g.Nhom like '%XH%' and g.Id_GroupQS like '%JH%';";
        List<Question> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
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
    public List<JobOfGroup> getJobAndGroup(){
        System.out.println("danh sach nganh va nhom");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from question_group where Id_GroupQS not like '%JH%';";
        List<JobOfGroup> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
            @Override
            public List<JobOfGroup> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                while(resultSet.next()){
                    JobOfGroup q = new JobOfGroup(resultSet);
                    list.add(q);
                }
                return list;
            }
        });
        System.out.println(list);
        return list;
    }
    public Job findJobByID(String id) {
    	jdbcTemplate = new JdbcTemplate(dataSource);
    	String sql = "select j.*\r\n" + 
    			"from question_group g join job j on g.Id_GroupQS = j.Id_Job\r\n" + 
    			"where g.Id_GroupQS like '" + id+"';";
    	 List<Job> list = new ArrayList<>();
         jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
             @Override
             public List<Job> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                 while(resultSet.next()){
                     Job q = new Job(resultSet);
                     list.add(q);
                 }
                 return list;
             }
         });
    	return list.get(0);
    }
    
    public List<Question> findQuestionOfJobByID(String id){
        System.out.println("tim cau hoi cua mot nganh");
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from question where Id_GroupQS like '"+ id +"';";
        List<Question> list = new ArrayList<>();
        jdbcTemplate.query(sql, new ResultSetExtractor<Object>() {
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
