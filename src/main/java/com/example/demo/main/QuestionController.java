package com.example.demo.main;

import com.example.demo.classifier.C45;
import com.example.demo.dao.DAO;
import com.example.demo.dto.AnswerDTO;
import com.example.demo.dto.Job;
import com.example.demo.dto.ScoreDTO;
import com.example.demo.dto.UserDTO;
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
	UserDTO uDto;
	C45 classifier ;
	String predictedValue = "";

	@GetMapping("/hello")
	public List<Question> getQuestionsJH() throws IOException {
		// get c45 tree
		String[] attributes = { "gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n",
				"do_chenh_lech real n", "John_holland real n", "diem_chuyen_sau real n", "type [tn,xh,FALSE] target" };
		Data data = new Data(attributes);
		List<Instance> instances = DAO.getTrainingData(data.getAttributes());
		data.setInstanceList(instances);
		System.out.println(data);
		C45 classifier = new C45(0, 5);
		classifier.train(data);
		classifier.printDecisionTree();

		// post question
		System.out.println("connect hello");
		List<Question> questionArrayList = new ArrayList<>();
		questionArrayList = DAO.getQuestionsTN();
		List<Question> show = new ArrayList<Question>();
		int count = 0;
		for (int i = 0; i < questionArrayList.size(); i++) {
			count = 0;
			for (int j = i + 1; j < questionArrayList.size(); j++) {
				if (questionArrayList.get(i).getIdQs().equals(questionArrayList.get(j).getIdQs())) {
					count++;
				}
			}
			if (count == 0)
				show.add(questionArrayList.get(i));
		}
		System.out.println(show);
		return show;
	}

	//nhan du lieu gt, diem tu client va gui cau hoi jh, chuyen sau xuong client
	@PostMapping("/Grquestion1_step1")
	public List<Question> getAnsWer1(String s) throws IOException {
		// get c45 tree
		String[] attributes = { "gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n",
				"do_chenh_lech real n", "John_holland real n", "diem_chuyen_sau real n", "type [tn,xh,FALSE] target" };
		Data data = new Data(attributes);
		List<Instance> instances = DAO.getTrainingData(data.getAttributes());
		data.setInstanceList(instances);
		System.out.println(data);
		classifier = new C45(0, 5);
		classifier.train(data);
		classifier.printDecisionTree();

		// receive data from client
		JsonObject jsonObject = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jsonObject = gson.fromJson(s, JsonObject.class);
		JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
        JsonElement sex = nameValuePair.get("sex");
		JsonElement score = nameValuePair.get("score");
		List<ScoreDTO> scoreDTOS = new ArrayList<>();
//        answerDTOS = gson.fromJson(ans,new TypeToken<List<AnswerDTO>>(){}.getType());
		scoreDTOS = gson.fromJson(score, new TypeToken<List<ScoreDTO>>() {
		}.getType());
		Byte sexDto = gson.fromJson(sex, new TypeToken<Byte>() {
		}.getType());
//        System.out.println(answerDTOS);
//        System.out.println(scoreDTOS);
//        UserDTO userDTO = new UserDTO(answerDTOS, scoreDTOS);
		uDto = new UserDTO();
		uDto.setSchoolScore(scoreDTOS);
		uDto.setSex(sexDto);
		if (uDto.isKhoiTN()) {
			List<Question> questionArrayList = new ArrayList<>();
			questionArrayList = DAO.getQuestionsTN();
			uDto.setListQs(questionArrayList);
			List<Job> lJob = new ArrayList<Job>();
			lJob = DAO.getJobTN();
			uDto.setListJob(lJob);
			List<Question> show = new ArrayList<Question>();
			int count = 0;
			for (int i = 0; i < questionArrayList.size(); i++) {
				count = 0;
				for (int j = i + 1; j < questionArrayList.size(); j++) {
					if (questionArrayList.get(i).getIdQs().equals(questionArrayList.get(j).getIdQs())) {
						count++;
					}
				}
				if (count == 0)
					show.add(questionArrayList.get(i));
			}
			System.out.println(show);
			return show;
			
		} else {
			List<Question> questionArrayList = new ArrayList<>();
			questionArrayList = DAO.getQuestionsXH();
			uDto.setListQs(questionArrayList);
			List<Job> lJob = new ArrayList<Job>();
			lJob = DAO.getJobXH();
			uDto.setListJob(lJob);
			List<Question> show = new ArrayList<Question>();
			int count = 0;
			for (int i = 0; i < questionArrayList.size(); i++) {
				count = 0;
				for (int j = i + 1; j < questionArrayList.size(); j++) {
					if (questionArrayList.get(i).getIdQs().equals(questionArrayList.get(j).getIdQs())) {
						count++;
					}
				}
				if (count == 0)
					show.add(questionArrayList.get(i));
			}
			System.out.println(show);
			return show;
		}
//        System.out.println(userDTO);
		// System.out.println(answerDTOS);
//        UserDTO userDTO = getSchoolScore();
	}
//    }
	
	// nhan dap an tu client va gui kq cuoi cung cho client
	@PostMapping("/Grquestion1_step2")
	public List<Job> showJob(String s) {
		
		// receive data from client
		JsonObject jsonObject = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jsonObject = gson.fromJson(s, JsonObject.class);
		JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
		JsonElement ans = nameValuePair.get("answer");
		List<AnswerDTO> answerDTOS = new ArrayList<AnswerDTO>();
		 answerDTOS = gson.fromJson(ans,new TypeToken<List<AnswerDTO>>(){}.getType());
		 uDto.setListAnswerDTOS(answerDTOS);
		 
		 //mining
		 LinkedHashMap<String, String> avp = new LinkedHashMap<>();
		 avp.put("gioi_tinh", uDto.getSex()+"");
		 avp.put("diem_tu_nhien", uDto.avgScoreTN()+"");
		 avp.put("diem_xa_hoi", uDto.avgScoreXH()+"");
		 avp.put("do_chenh_lech", (uDto.avgScoreTN()-uDto.avgScoreXH())+"");
		 avp.put("John_holland", uDto.avgJohnHolland()+"");
		 avp.put("diem_chuyen_sau", uDto.avgChuyenSau()+"");
		 Instance test = new Instance(avp, "Test");
		 predictedValue = classifier.predict(test, classifier.getDecisionTree());
		 
		 //show top 3 job
		 return uDto.showTop3(predictedValue);
		 
	}
	//mo rong khi step2 tra ve mang rong do mining gia tri = false
	//client chuyen ve trang nay de tiep tuc tra loi cau hoi
	@GetMapping("//Grquestion1_step3_Expand")
	public List<Question> getQuestionsEx() throws IOException {
		List<Question> questionArrayList = new ArrayList<>();
		List<Question> show = new ArrayList<Question>();
		if (uDto.isKhoiTN()) {
			questionArrayList = DAO.getQuestionsXH();
			uDto.setListQs(questionArrayList);
			List<Job> lJob = new ArrayList<Job>();
			lJob = DAO.getJobXH();
			uDto.setListJob(lJob);
			int count = 0;
			for (int i = 0; i < questionArrayList.size(); i++) {
				count = 0;
				for (int j = i + 1; j < questionArrayList.size(); j++) {
					if (questionArrayList.get(i).getIdQs().equals(questionArrayList.get(j).getIdQs())) {
						count++;
					}
				}
				if (count == 0)
					show.add(questionArrayList.get(i));
			}
		}else {
			questionArrayList = DAO.getQuestionsTN();
			uDto.setListQs(questionArrayList);
			List<Job> lJob = new ArrayList<Job>();
			lJob = DAO.getJobTN();
			uDto.setListJob(lJob);
			int count = 0;
			for (int i = 0; i < questionArrayList.size(); i++) {
				count = 0;
				for (int j = i + 1; j < questionArrayList.size(); j++) {
					if (questionArrayList.get(i).getIdQs().equals(questionArrayList.get(j).getIdQs())) {
						count++;
					}
				}
				if (count == 0)
					show.add(questionArrayList.get(i));
			}
			System.out.println(show);
		}
		
		return show;
	}

	// nhan dap an tu client va gui kq cuoi cung cho client
	// neu mang rong thi thong bao ban khong phu hop voi nganh nghe nao
	@PostMapping("/Grquestion1_step4_Expand")
	public List<Job> showJobEx(String s) {
		// receive data from client
				JsonObject jsonObject = new JsonObject();
				Gson gson = new GsonBuilder().create();
				jsonObject = gson.fromJson(s, JsonObject.class);
				JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
				JsonElement ans = nameValuePair.get("answer");
				List<AnswerDTO> answerDTOS = new ArrayList<AnswerDTO>();
				 answerDTOS = gson.fromJson(ans,new TypeToken<List<AnswerDTO>>(){}.getType());
				 uDto.setListAnswerDTOS(answerDTOS);
				 
				//mining
				 LinkedHashMap<String, String> avp = new LinkedHashMap<>();
				 avp.put("gioi_tinh", uDto.getSex()+"");
				 avp.put("diem_tu_nhien", uDto.avgScoreTN()+"");
				 avp.put("diem_xa_hoi", uDto.avgScoreXH()+"");
				 avp.put("do_chenh_lech", (uDto.avgScoreTN()-uDto.avgScoreXH())*-1+"");
				 avp.put("John_holland", uDto.avgJohnHolland()+"");
				 avp.put("diem_chuyen_sau", uDto.avgChuyenSau()+"");
				 Instance test = new Instance(avp, "Test");
				 predictedValue = classifier.predict(test, classifier.getDecisionTree());
				 
				 //show top 3 job
				 return uDto.showTop3(predictedValue);
				 
	}
	
	@PostMapping("/demo")
	public UserDTO getSchoolScore(String s) {
		System.out.println("connect");
		HashMap<String, HashMap<String, String>> list = new HashMap<>();
		Gson gsonBuilder = new GsonBuilder().create();

		Type listType = new TypeToken<HashMap<String, HashMap<String, String>>>() {
		}.getType();
		list = gsonBuilder.fromJson(s, listType);
		System.out.println(s);
		System.out.println(list);
		UserDTO userDTO = new UserDTO();
//        userDTO.setSchoolScore(list);
		return userDTO;
	}
//    @PostMapping("/answer")
//    public String getAnswer(String  s){
////        System.out.println("connect Ansssss");
////        System.out.println(s);
//            }

}
