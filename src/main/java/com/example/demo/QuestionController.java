package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
public class QuestionController {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping("/hello")
    public List<Question> getQuestions( ModelMap modelMap){
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM demo1.Question;";
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

    public List<Question> randomQuestion(List<Question> list){

        return list;

    }
}
