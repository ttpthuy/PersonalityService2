package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.sql.DataSource;
import java.lang.reflect.Type;
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
    public List<Question> getQuestions(ModelMap modelMap){
        System.out.println("connect hello");
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

    @PostMapping("/demo")
    public String demo(@RequestParam String s){
        System.out.println("connect");
        List<HashMap<String, HashMap<String, String>>> list = new ArrayList<>();
        Gson gsonBuilder = new GsonBuilder().create();

        Type listType = new TypeToken<List<HashMap<String, HashMap<String, String>>>>() {}.getType();
        list =  gsonBuilder.fromJson(s, listType);
        System.out.println(s);
        System.out.println(list);
        HashMap<String, String> lop10 = new HashMap<>();
        HashMap<String,HashMap<String, String>> json = new HashMap<>();
        return "connect";
    }

}
