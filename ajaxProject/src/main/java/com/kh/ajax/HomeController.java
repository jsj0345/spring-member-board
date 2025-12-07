package com.kh.ajax;

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
	
	
	
	
}
