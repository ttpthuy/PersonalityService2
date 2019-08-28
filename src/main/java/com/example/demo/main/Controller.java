package com.example.demo.main;

import com.example.demo.classifier.C45;
import com.example.demo.dao.DAO;
import com.example.demo.dto.AnswerDTO;
import com.example.demo.dto.Job;
import com.example.demo.dto.JobOfGroup;
import com.example.demo.dto.Question;
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
public class Controller {
	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DAO DAO;
	UserDTO uDto;
	C45 classifier;
	String predictedValue = "";

	//test
	@GetMapping("/hello")
	public List<Question> getQuestionsJH() throws IOException {
		// get c45 tree
//		String[] attributes = { "gioi_tinh text n", "diem_tu_nhien real n", "diem_xa_hoi real n",
//				"do_chenh_lech real n", "John_holland real n", "diem_chuyen_sau real n", "type [tn,xh,FALSE] target" };

		String[] attributes = { "gioi_tinh text n", "diem_so real n", "Diem_John_Holand real n",
				"Diem_chuyen_sau real n", "Nhu_cau real n", "type [TRUE,FALSE] target" };
		Data data = new Data(attributes);
//		List<Instance> instances = DAO.getTrainingData(data.getAttributes());
		List<Instance> instances = DAO.getTrainingData2(data.getAttributes());
		data.setInstanceList(instances);
		System.out.println(data);
		C45 classifier = new C45(0, 5);
		classifier.train(data);
		classifier.printDecisionTree();
		classifier.accuracy(data.getInstanceList(), classifier.getDecisionTree());
		classifier.crossValidation(data, 10);

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
//		System.out.println(show);
		return show;
	}

	// nhan du lieu gt, diem tu client va gui cau hoi jh, chuyen sau xuong client
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
		
//		System.out.println(scoreDTOS);
		Byte sexDto = gson.fromJson(sex, new TypeToken<Byte>() {
		}.getType());
//        System.out.println(answerDTOS);
//        System.out.println(scoreDTOS);
//        UserDTO userDTO = new UserDTO(answerDTOS, scoreDTOS);
		uDto = new UserDTO();
		uDto.setSchoolScore(scoreDTOS);
		uDto.setSex(sexDto);
		if (uDto.isKhoiTN()) {
			
			System.out.println("khoi tn");
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
			System.out.println("in show");
			System.out.println(show);
			return show;

		} else {
			System.out.println("khoi xh");
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
			System.out.println("in show");
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
		answerDTOS = gson.fromJson(ans, new TypeToken<List<AnswerDTO>>() {
		}.getType());
		uDto.setListAnswerDTOS(answerDTOS);

		// mining
		LinkedHashMap<String, String> avp = new LinkedHashMap<>();
		avp.put("gioi_tinh", uDto.getSex() + "");
		avp.put("diem_tu_nhien", uDto.avgScoreTN() + "");
		avp.put("diem_xa_hoi", uDto.avgScoreXH() + "");
		avp.put("do_chenh_lech", (uDto.avgScoreTN() - uDto.avgScoreXH()) + "");
		avp.put("John_holland", uDto.avgJohnHolland() + "");
		avp.put("diem_chuyen_sau", uDto.avgChuyenSau() + "");
		System.out.println(avp);
		Instance test = new Instance(avp, "Test");
		predictedValue = classifier.predict(test, classifier.getDecisionTree());
		System.out.println("ket qua: "+predictedValue);

		// show top 3 job
		System.out.println("show top 3");
		System.out.println(uDto.showTop3(predictedValue));
		return uDto.showTop3(predictedValue);

	}

	// mo rong khi step2 tra ve mang rong do mining gia tri = false
	// client chuyen ve trang nay de tiep tuc tra loi cau hoi
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
		} else {
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
		answerDTOS = gson.fromJson(ans, new TypeToken<List<AnswerDTO>>() {
		}.getType());
		uDto.setListAnswerDTOS(answerDTOS);

		// mining
		LinkedHashMap<String, String> avp = new LinkedHashMap<>();
		if(uDto.getSex()==0) {
			avp.put("gioi_tinh", "nam");
		}else {
			avp.put("gioi_tinh", "nu");
		}
		avp.put("diem_tu_nhien", uDto.avgScoreTN() + "");
		avp.put("diem_xa_hoi", uDto.avgScoreXH() + "");
		avp.put("do_chenh_lech", (uDto.avgScoreTN() - uDto.avgScoreXH()) * -1 + "");
		avp.put("John_holland", uDto.avgJohnHolland() + "");
		avp.put("diem_chuyen_sau", uDto.avgChuyenSau() + "");
		System.out.println(avp);
		Instance test = new Instance(avp, "Test");
		predictedValue = classifier.predict(test, classifier.getDecisionTree());

		// show top 3 job
		return uDto.showTop3(predictedValue);

	}

	// gui danh sach nghe va nhom cua cac nganh nghe do xuong client
	@GetMapping("//Grquestion2_step1")
	public List<JobOfGroup> getlJob() throws IOException {
		List<JobOfGroup> lJob = new ArrayList<JobOfGroup>();
		lJob = DAO.getJobAndGroup();
		return lJob;
	}

	// nhan ve mot nghe(JobOfGroup) va diem so tu client
	// gui danh sach cau hoi xuong client
	@PostMapping("/Grquestion2_step2")
	public List<Question> showQuestions(String s) {
		// receive data from client
		JsonObject jsonObject = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jsonObject = gson.fromJson(s, JsonObject.class);
		JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
		JsonElement jobOfG = nameValuePair.get("jobOfG");
		JobOfGroup jog = gson.fromJson(jobOfG, new TypeToken<JobOfGroup>() {
		}.getType());
		JsonElement score = nameValuePair.get("score");
		List<ScoreDTO> scoreDTOS = new ArrayList<>();
		scoreDTOS = gson.fromJson(score, new TypeToken<List<ScoreDTO>>() {
		}.getType());
		// set udto
		Job job = DAO.findJobByID(jog.getId());
		uDto = new UserDTO();
		uDto.setJobTest(job);
		uDto.setSchoolScore(scoreDTOS);

		// gui danh sach cau hoi xuong client
		List<Question> lQuestions = new ArrayList<Question>();
		lQuestions = DAO.findQuestionOfJobByID(jog.getId());
		List<Question> lQuestionsJH = new ArrayList<Question>();
		
		if(uDto.avgScoreTN()>=uDto.avgScoreXH()) {
			lQuestionsJH = DAO.getQuestionsJH_TN();
		}else {
			lQuestionsJH = DAO.getQuestionsJH_XH();
		}
		for (int i = 0; i < lQuestionsJH.size(); i++) {
			lQuestions.add(lQuestionsJH.get(i));
		}
		uDto.setListQs(lQuestions);
		return lQuestions;
	}

	// nhan dap an tu client
	// gui kq cuoi cung la 1 job(id , name , do tuong thich(score))
	// neu khong tuong thich thi thuoc tinh score trong lop job = 0;
	@PostMapping("/Grquestion2_step3")
	public Job showResult(String s) throws IOException {
		// receive data from client
		JsonObject jsonObject = new JsonObject();
		Gson gson = new GsonBuilder().create();
		jsonObject = gson.fromJson(s, JsonObject.class);
		JsonObject nameValuePair = jsonObject.get("nameValuePairs").getAsJsonObject();
		JsonElement ans = nameValuePair.get("answer");
		List<AnswerDTO> answerDTOS = new ArrayList<AnswerDTO>();
		answerDTOS = gson.fromJson(ans, new TypeToken<List<AnswerDTO>>() {
		}.getType());
		uDto.setListAnswerDTOS(answerDTOS);

		// mining
		//training
		String[] attributes = { "gioi_tinh text n", "diem_so real n", "Diem_John_Holand real n",
				"Diem_chuyen_sau real n", "Nhu_cau real n", "type [TRUE,FALSE] target" };
		Data data = new Data(attributes);
		List<Instance> instances = DAO.getTrainingData2(data.getAttributes());
		data.setInstanceList(instances);
		System.out.println(data);
		C45 classifier = new C45(0, 5);
		classifier.train(data);
		//test
		LinkedHashMap<String, String> avp = new LinkedHashMap<>();
		if (uDto.getJobTest().isCoincidence(uDto.getSex())) {
			avp.put("gioi_tinh", "hop");
		}else {
			avp.put("gioi_tinh", "it_hop");
		}
		if(uDto.avgScoreTN()>=uDto.avgScoreXH()) {
			avp.put("diem_so", uDto.avgScoreTN()+"");
		}else {
			avp.put("diem_so", uDto.avgScoreXH()+"");
		}
		avp.put("Diem_John_Holand", uDto.avgJohnHolland()+"");
		avp.put("Diem_chuyen_sau", uDto.avgChuyenSau()+"");
		avp.put("Nhu_cau", uDto.getJobTest().getJobMarket()+"");
		Instance test = new Instance(avp, "Test");
		predictedValue = classifier.predict(test, classifier.getDecisionTree());
		
		
		System.out.println(uDto.showResult(predictedValue));
		return uDto.showResult(predictedValue);
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
