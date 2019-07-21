package com.example.demo.main;

import com.example.demo.classifier.C45;
import com.example.demo.dto.AnswerDTO;
import com.example.demo.dto.AnswerNewDTO;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.TrainingData;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.example.demo.data.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.demo.types.Instance;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@RestController
public class QuestionController {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DAO DAO;
    @GetMapping("/hello")
    public List<Question> getQuestions(){
        System.out.println("connect hello");
        List<Question> questionArrayList = new ArrayList<>();
        questionArrayList = DAO.getQuestions2();
        return questionArrayList;
    }
    @GetMapping("/test")
        public List<TrainingData> get(){
            List<TrainingData> instances = DAO.getTraining();
          return instances;

    }

    @GetMapping("/data-training")
    public void c45() throws IOException {
        String[] attributes = {"gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n", "do_chenh_lech real n",
                "John_holland real n","diem_chuyen_sau real n","type [tn,xh,FALSE] target" };
        Data data = new Data(attributes);
        List<Instance> instances = DAO.getQuestions(data.getAttributes());
        data.setInstanceList(instances);
        System.out.println(data);
        C45 classifier = new C45(0, 5);

        classifier.train(data);
        classifier.printDecisionTree();
    }
    @GetMapping("/com/example/demo/data")
    public List<Instance> data(){
        List<Instance> instances = new ArrayList<>();
//         instances = new TrainingDataDAO().processInstanceList();
         return instances;
    }


    @PostMapping("/demo")
    public UserDTO getSchoolScore(String s){
        System.out.println("connect");
        HashMap<String, HashMap<String, String>> list = new HashMap<>();
        Gson gsonBuilder = new GsonBuilder().create();

        Type listType = new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType();
        list =  gsonBuilder.fromJson(s, listType);
        System.out.println(s);
        System.out.println(list);
        UserDTO userDTO = new UserDTO();
//        userDTO.setSchoolScore(list);
        return userDTO;
    }
    @PostMapping("/answer")
    public String getAnswer(String  s){
//        System.out.println("connect Ansssss");
//        System.out.println(s);
        JsonObject jsonObject = new JsonObject();
        Gson gson = new GsonBuilder().create();
        jsonObject = gson.fromJson(s, JsonObject.class);
        JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
        JsonElement ans = nameValuePair.get("answer");
        JsonElement score = nameValuePair.get("score");
//        System.out.println(ans);
//        System.out.println(score);
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        List<ScoreDTO> scoreDTOS = new ArrayList<>();
        answerDTOS = gson.fromJson(ans,new TypeToken<List<AnswerDTO>>(){}.getType());
        scoreDTOS = gson.fromJson(score,new TypeToken<List<ScoreDTO>>(){}.getType());
//        System.out.println(answerDTOS);
//        System.out.println(scoreDTOS);
        UserDTO userDTO = new UserDTO(answerDTOS, scoreDTOS);
        System.out.println(userDTO);
        //        System.out.println(answerDTOS);
//        UserDTO userDTO = getSchoolScore();
        return "connect";
    }


}
