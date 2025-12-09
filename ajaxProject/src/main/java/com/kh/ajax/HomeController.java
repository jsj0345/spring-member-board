package com.kh.ajax;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class HomeController {
	
	/*
	@RequestMapping("/ajax.do")
	public void ajax(String name,int age,HttpServletResponse response) throws IOException {
		
		System.out.println("요청확인");
		System.out.println("name : " + name);
		System.out.println("age : " + age); //정보는 갔는데 통신이 실패됨.
		
		//요청 데이터를 받았으니 응답데이터를 전달하기
		//응답데이터 전달 방식
		//1)HttpServletResponse 객체 이용하여 스트림처리
		
		String responseStr = "이름은 : " + name + ",나이는 : " +age+"살 입니다.";
		
		//인코딩처리
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().println(responseStr);	
	}
	*/
	
	/*
	//mapping 어노테이션에 추가 속성을 작성할땐 매핑주소도 꼭 속성과 함께 작성해야한다. 
	@ResponseBody // 응답하고 있는 것이 데이터 자체로 응답하겠다라는 의미.
	@RequestMapping(value="/ajax.do", produces = "text/html;charset=UTF-8") //여러 속성들을 넣을거면 확실하게 적어줘야한다.
	public String ajax(String name,int age) throws IOException {
		
		System.out.println("요청확인");
		System.out.println("name : " + name);
		System.out.println("age : " + age); //정보는 갔는데 통신이 실패됨.
		
		//요청 데이터를 받았으니 응답데이터를 전달하기
		//응답데이터 전달 방식
		//2)문자열로 반환하기
		
		String responseStr = "이름은 : " + name + ",나이는 : " +age+"살 입니다.";
		//spring에서 응답데이터로 문자열을 작성하면 기본적으로 viewResolver가 
		//앞뒤로 /WEB-INF/views/ 와 .jsp를 붙여서 요청해준다.
		//문자열 자체로(데이터 자체로) 응답하고자 한다면 해당 메소드에 
		//@ResponseBody 어노테이션을 부여해야한다. 
		//인코딩처리 및 응답데이터 형식은 Mapping어노테이션에 같이 작성한다.
		//produces 속성에 작성하기 
		
		
		return responseStr;
	}
	*/
	
	/*
	@ResponseBody
	@RequestMapping(value="/ajax.dodo",produces="text/html;charset=UTF-8")
	public String ajax2(String menu, int price) throws IOException {
		 
		String responseStr = menu + "메뉴의 가격은 " + price +"원 입니다.";
		
		return responseStr; 
	}
	*/
	
	/*
	@ResponseBody // 뷰리졸버 작동 막기 위함 자동으로 물리적인 경로 반환하려고 하는 특성땜에 데이터 자체를 반환할거면 ResponseBody
	@RequestMapping(value="/select.me",produces="application/json;charset=UTF-8")
	public String selectMember(int userNo) {
		
		//,produces="application/json;charset=UTF-8"
		
		System.out.println(userNo);
		//위에서 전달받은 번호를 이용하여 데이터베이스에서 회원정보를 조회해왔다고 가정하기
		
		Member m = Member.builder().name("김유저")
				                   .age(25)
				                   .gender("남자")
				                   .email("user01@naver.com")
				                   .build();
		
		//위에서 받아온 데이터를 json 형식으로 변경하기 
		//JSONObject (객체형태) - map과 유사한 형태 
		JSONObject jobj = new JSONObject();
		jobj.put("name", m.getName());
		jobj.put("age", m.getAge());
		jobj.put("gender", m.getGender());
		jobj.put("email", m.getEmail());
		
		System.out.println("---jobj---");
		System.out.println(jobj);
		System.out.println("jobj.toJSONString--");
		System.out.println(jobj.toJSONString());
		
		//JSON 형식을 반환하려면 응답형태에
		//produces = "application/json;charset=UTF-8"
		//와 같이 추가해야한다.
		
		return jobj.toJSONString(); // json 객체를 문자열로 변환해주는 메소드  
		
		
	}
	*/
	
	
	//위 작업을 GSON을 이용하여 쉽게 해보기
	@ResponseBody // 뷰리졸버 작동 막기 위함 자동으로 물리적인 경로 반환하려고 하는 특성땜에 데이터 자체를 반환할거면 ResponseBody
	@RequestMapping(value="/select.me",produces="application/json;charset=UTF-8")
	public Member selectMember(int userNo) {
		
		System.out.println(userNo);
		//위에서 전달받은 번호를 이용하여 데이터베이스에서 회원정보를 조회해왔다고 가정하기
		
		Member m = Member.builder().name("김유저")
				                   .age(25)
				                   .gender("남자")
				                   .email("user01@naver.com")
				                   .build();
		
		//Gson 라이브러리에서 제공하는 메소드
		//객체를 json화 시켜주는 메소드 : toJson(obj)
		
		System.out.println(new Gson().toJson(m));
	
		return m; // json 객체를 문자열로 변환해주는 메소드  
			
	}
	
	@ResponseBody
	@RequestMapping(value="/selectList", produces="application/json;charset=UTF-8")
	public String selectList() {
		Member m = new Member("김유저","남자",25,"user01@gmail.com");
		Member m2 = new Member("박사원","여자",35,"sawon@naver.com");
		Member m3 = new Member("최학생","남자",20,"student@gmail.com");
		
		ArrayList<Member> list = new ArrayList<>();
		list.add(m);
		list.add(m2);
		list.add(m3);
		
		//위에 있는 목록을 데이터베이스에서 조회해왔다고 가정하고 진행
		//JSON화 시켜서 응답데이터로 보내기
		/*
		JSONObject jobj = new JSONObject();
		jobj.put("name", m.getName());
		jobj.put("gender", m.getGender());
		jobj.put("age", m.getAge());
		jobj.put("email", m.getEmail());
		
		JSONObject jobj2 = new JSONObject();
		jobj.put("name", m2.getName());
		jobj.put("gender", m2.getGender());
		jobj.put("age", m2.getAge());
		jobj.put("email", m2.getEmail());
		
		JSONObject jobj3 = new JSONObject();
		jobj.put("name", m3.getName());
		jobj.put("gender", m3.getGender());
		jobj.put("age", m3.getAge());
		jobj.put("email", m3.getEmail());
		
		//JSONArray에 목록 넣어주기
		JSONArray jArr = new JSONArray();
		jArr.add(jobj);
		jArr.add(jobj2);
		jArr.add(jobj3);
		
		위 작업을 반복문으로 줄여보기 
		*/
		
		//목록 넣을 JSONArray 준비
		JSONArray jArr = new JSONArray();
		for(Member mem : list) { //담겨있는 모든 데이터 접근
			
			//각 객체정보는 JSONObject에 넣어서 목록에 추가하기
			JSONObject jobj = new JSONObject();
			
			jobj.put("name", mem.getName());
			jobj.put("gender", mem.getGender());
			jobj.put("age", mem.getAge());
			jobj.put("email", mem.getEmail());
			
			jArr.add(jobj); 
			
			
		}
		
		//toJSONString 메소드로 문자열화 시키기 
		return jArr.toJSONString();
		
		
		
	}
	
	@ResponseBody
	@RequestMapping(value="/selectList2", produces="application/json;charset=UTF-8")
	public String selectList2() {
		Member m = new Member("김유저","남자",25,"user01@gmail.com");
		Member m2 = new Member("박사원","여자",35,"sawon@naver.com");
		Member m3 = new Member("최학생","남자",20,"student@gmail.com");
		
		ArrayList<Member> list = new ArrayList<>();
		list.add(m);
		list.add(m2);
		list.add(m3);
		
		//GSON 이용하여 반환하기
		//GSON이 제공하는 toJSON 메소드로 간단하게 처리
		//GSON.toJSON(객체); 전달된 객체를 json화 시킨후 문자열 처리 해주는 메소드
		
		
		
		return new Gson().toJson(list);
	}	
	
	@ResponseBody
	@RequestMapping(value="/selectList3", produces="application/json;charset=UTF-8")
	public ArrayList<Member> selectList3() {
		Member m = new Member("김유저","남자",25,"user01@gmail.com");
		Member m2 = new Member("박사원","여자",35,"sawon@naver.com");
		Member m3 = new Member("최학생","남자",20,"student@gmail.com");
		
		ArrayList<Member> list = new ArrayList<>();
		list.add(m);
		list.add(m2);
		list.add(m3);
		
		return list; 
	}
	
	
	
	
}
