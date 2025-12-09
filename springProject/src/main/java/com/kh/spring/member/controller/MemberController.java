package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;


//Controller 타입의 어노테이션을 부여하면 spring이 bean scan을 통해 Controller bean으로 등록한다.
@Controller 
public class MemberController {
	
	//사용할 Service객체를 선언해놓고 스프링이 관리 할 수 있도록 처리 
	//기존방식 
	//private MemberService service = new MemberServiceImpl();
	/*
	 * 기존 객체 생성 방식
	 * 객체간의 결합도가 높다(소스코드의 수정이 일어날 경우 해당 객체에 대해서 수정작업 처리를 해야함)
	 * 서비스가 동시에 많은 횟수가 요청될 경우 그만큼 개겣 생성이 발생됨
	 * 
	 * spring에선 DI(Dependency Injection)을 이용한 방식으로 
	 * 객체의 생명주기를 관리한다(개겣간의 결합도를 낮춰준다
	 * new라는 생성 키워드 없이 선언문과 @Autowired 라는 어노테이션을 부여함으로
	 * 스프링이 해당 Bean을 관리 할 수 있도록 한다.
	 * 
	 * @Autowired : 스프링이 bean을 자동주입할 수 있도록 하는 어노테이션
	 * 
	 * */
	
	@Autowired
	private MemberService service;
	
	//BcryptPasswordEncoder 사용하기 위해서 스프링에게 주입 처리 하기 
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	/*
	 * Spring에서 파라미터(요청시 전달값)를 받아 주는 방법
	 * 
	 * 1.HttpServletRequest 객체 이용 (기존 jsp/servlet방식)
	 * 요청을 처리하는 메소드의 매개변수에 필요한 객체를 입력하면 spring이 메소드 호출 시 자동으로 해당 객체를 
	 * 생성하여 주입해준다.
	 * */
	//@RequestMapping("매핑주소") : 요청시 입력한 매핑주소를 받아주는 어노테이션 
	
	/*
	@RequestMapping("/login.me")
	public String loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		System.out.println(userId);
		System.out.println(userPwd);
		
		//요청에 대한 응답 처리는 문자열로 경로를 작성할 수 있다.
		//요청 위임시 작성된 경로에 viewResolver가 앞쪽엔 /WEB-INF/views/ 경로를 
		//뒤쪽엔 .jsp 경로를 붙여주기 때문에 그 사이에 있는 부분만 작성하면 된다.
		//만약 재요청(redirect)방식을 한다면 "redirect:경로" 와 같이 작성한다.
		
		//요청 위임방식 
		//return "main"; // /WEB-INF/views/main.jsp 로 요청되는것
		return "redirect:/"; // redirect 에서 /는 컨텍스트루트 기준으로 시작된다.
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션 이용
	 * request.getParameter("키")로 값을 추출하는 역할을 대신 수행하는 어노테이션
	 * value 속성의 값으로 jsp에서 작성했던 name 속성값을 담으면 해당 매개변수로 받아 올 수 있다.
	 * 만약 전달된 값이 비어있는 상태라면 defaultValue 속성으로 기본값을 지정해 줄 수 있다.
	 * defaultValue="문자열" - 값을 제시할땐 문자열로 제시하여야하고 
	 * 해당 매개변수에선 원하는 자료형으로 처리하면 된다.
	 * */
	
	/*
	@RequestMapping("/login.me")
	public String loginMember(@RequestParam(value="userId") String userId
							 ,@RequestParam(value="userPwd") String userPwd
							 ,@RequestParam(value="currentPage",defaultValue = "1")
							  int currentPage) {
		
		System.out.println("userId : "+userId);
		System.out.println("userPwd : "+userPwd);
		System.out.println("currentPage : "+(currentPage+10));
		
		
		return "redirect:/";
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 단, 매개변수명을 jsp의 name 속성값과 일치시켜야만 해당 데이터가 파싱된다.
	 * 또한 어노테이션을 생략했기 때문에 defaultValue는 사용할 수 없다.
	 * */
	
	/*
	@RequestMapping("/login.me")
	public String loginMember(String userId,String userPwd,String name) {
		
		System.out.println(userId+" "+userPwd);
		System.out.println("확인 : "+name);//키값 찾지못하니 String의 기본값 null반환
		
		
		return "redirect:/";
	}
	*/
	
	/*
	 * 4.커맨드 객체 방식
	 * 해당 메소드의 매개변수로 요청시 전달값을 담고자 하는 VO를 세팅후
	 * 요청시 전달값의 key값(jsp의 name속성값)을 VO에 각 필드명으로 작성하면
	 * 스프링컨테이너가 해당 객체를 기본생성자로 생성 후 내부적으로 setter메소드를 찾아 
	 * 요청시 전달값을 해당 필드에 담아서 반환해준다.
	 * 
	 * 반드시 name속성값과 필드명이 같아야한다.
	 * 
	 * 요청 처리 후 응답 데이터를 담고 응답 페이지로 포워딩 또는 재요청 해보기
	 * HttpSession이 필요하다면 매개변수에 작성 ->스프링이 객체 주입해준다.
	 * 
	 * 응답뷰로 포워딩(위임)시 전달데이터 담을 수 있는 객체
	 * 1.model
	 * requestScope를 담당하는 객체로 key value세트로 데이터를 담아서 전달할 수 있다.
	 * 데이터 담는 메소드는 기존에 사용하던 setAttribute가 아닌 addAttribute 이다.	 
	 * */
	
	@RequestMapping("/login.me")
	public String loginMember(Member m,HttpSession session,Model model) {
		
		//전달받은 데이터로 데이터베이스 조회해오기
		//System.out.println(m);
		//사용자가 입력한 비밀번호를 추출하여 사용자가 입력했던 비밀번호를 암호화한 암호문과 비교하여 
		//암호문을 복호화했을때 입력한 평문이 나오는지 검증하는 메소드를 통해서 비밀번호를 확인한다.
		//Bcrypt 는 암호화된 암호문을 평문으로 반환해주지는 않는다(검증 작업처리만 해줌)
		//검증 메소드 : bcrypt.matches(평문,암호문) : 평문과 암호문을 비교하여 같으면 true 다르면 false
		//암호문 변경 메소드 : bcrypt.encode(평문) : 암호문 문자열을 반환
		//System.out.println("사용자가 입력한 비밀번호평문 : "+m.getUserPwd());
		//System.out.println("해당 비밀번호를 암호문으로 변경 : "+bcrypt.encode(m.getUserPwd()));
		
		//전달받은 비밀번호를 아이디로 조회한 회원정보의 비밀번호와 복호화 검증을 했을때
		//true가 나왔다면 로그인 성공! 메시지와 함께 로그인 정보 세션에 담기
		//false라면 로그인 실패! 메시지를 세션에 담기
		//위 메시지는 session에 담아서 처리할것
		//각각 성공시 메인페이지로 재요청
		//실패시 에러페이지로 포워딩
		
		Member loginMember = service.loginMember(m);
		//위에 조회된 loginMember가 null 이 아닌 경우에만 비밀번호 검증 처리하기 
		
		System.out.println(loginMember);
		
		//null이 아니고 판별이 true라면 성공
		if(loginMember!=null && bcrypt.matches(m.getUserPwd(), loginMember.getUserPwd())) {
			session.setAttribute("alertMsg", "로그인 성공!"); 
			session.setAttribute("loginMember", loginMember);//로그인 정보 넣기 
			
			//메인으로 재요청
			return "redirect:/";
		}else {
			session.setAttribute("errorMsg", "로그인 실패!");
			
			//에러페이지로 포워딩
			return "common/errorPage";
		}
	}
	
	
	//로그아웃
	@RequestMapping("/logout.me")
	public String logout(HttpSession session,HttpServletRequest request) {
		
		//로그인된 회원정보 지워주기
		session.removeAttribute("loginMember");
		
		//이전경로 찾아오기 
		String path = request.getHeader("referer");
		
		//조건처리 
		if(path==null) {
			return "redirect:/";//메인페이지로
		}
		
		return "redirect:"+path;
	}
	
	
	/*
	 	응답뷰로 포워딩(위임)시 전달데이터 담을 수 있는 객체
	 * 1.model
	 * requestScope를 담당하는 객체로 key value세트로 데이터를 담아서 전달할 수 있다.
	 * 데이터 담는 메소드는 기존에 사용하던 setAttribute가 아닌 addAttribute 이다.	 
	 * */
	//마이페이지 이동 
	@RequestMapping("/mypage.me")
	public String mypage(Model model) {
		
		//마이페이지로 이동시키기 
		//model.addAttribute("alertMsg","모델로 데이터 넘기기");
		
		return "member/mypage";
	}
	
	
	//회원가입 페이지로 이동 메소드
	@RequestMapping("/enrollForm.me")
	public String enrollForm() {
		
		return "member/memberEnrollForm";
	}
	
	
	//회원가입 요청시 처리 메소드 
	@RequestMapping("/insert.me")
	public String insertMember(Member m,HttpSession session) {
		//web.xml에 인코딩 필터 설정 후 처리하기 
		//System.out.println(m);
		
		//데이터베이스에 등록정보 넣기 
		//비밀번호가 평문이기 때문에 데이터베이스에 등록할때는 암호문으로 바꿔서 등록하기
		m.setUserPwd(bcrypt.encode(m.getUserPwd()));
		
		int result = service.insertMember(m);
		
		if(result>0) {//회원가입 성공
			session.setAttribute("alertMsg", "회원가입 성공!");
			return "redirect:/";
			
		}else { //회원가입 실패 
			session.setAttribute("errorMsg", "회원가입 실패!");
			//위임
			return "common/errorPage";
		}
	}
	
	
	//회원탈퇴
	@RequestMapping("/delete.me")
	public String deleteMember(Member m,HttpSession session) {		
		
		
		//기존에 만들어둔 메소드 활용(로그인 데이터 조회해오기)
		Member loginMember = service.loginMember(m);
//		실패시 실패메시지와 함께 마이페이지로
//		성공시 로그인 풀고 메인페이지로 메시지도 함께 (메시지 자유)
		if(loginMember!=null&&bcrypt.matches(m.getUserPwd(), loginMember.getUserPwd())) {
			
			//회원 탈퇴 처리 요청하기 
			int result = service.deleteMember(m.getUserId());
			
			if(result>0) {//탈퇴 성공
				//탈퇴되었으니 로그인을 해제시키고 메인페이지로 보내기
				session.removeAttribute("loginMember");//로그인 해제
				session.setAttribute("alertMsg", "그동안 서비스를 이용해주셔서 감사합니다.");
				return "redirect:/";
				
			}else {//탈퇴 실패
				session.setAttribute("alertMsg", "탈퇴 요청이 실패하였습니다. 관리자에게 문의하세요.");
				return "redirect:mypage.me";
			}
		}else { //로그인정보가 없거나 비밀번호가 일치하지 않는 경우 
			session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			
			return "redirect:mypage.me";
		}
		
	}
	
	
	//회원 정보 수정
	@RequestMapping("/update.me")
	public String updateMember(Member m,HttpSession session) {
		
		int result = service.updateMember(m);
		
//		성공시 로그인정보 갱신,정보수정 성공 메시지 / 마이페이지로 이동
//		실패시 정보수정 실패 메시지 alert / 마이페이지로 이동  
		
		if(result>0) {//성공
			Member loginMember = service.loginMember(m);
			
			session.setAttribute("loginMember", loginMember);
			session.setAttribute("alertMsg", "정보 수정 성공!");
		
		}else {//실패
			
			session.setAttribute("alertMsg", "정보 수정 실패!");
		}
		
		
		
		return "redirect:mypage.me";
		
	}
	
	//회원 중복 체크
	@RequestMapping(value="/idcheck.me", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String idCheck(String inputId) {
		
		Member m = service.selectMember(inputId); // 데이터베이스에서 회원 조회 
		
		if(m!=null) {
			
		  return "NNNNN";
			
		} 
		
		return "NNNNY"; 
		
		/*
		 * int count = service.idCheck(inputId); 
		 * 
		 * if(count>0) {
		 *    return "NNNNN";
		 * } else {
		 *    return "NNNNY";   
		 * }   
		 */
		
	}
	
	/*
	 * 
	 * 
	 * @ResponseBody
	 * @RequestMapping("/idcheck.me")
	 * public int idcheck(String inputId) {
	 * 
	 *   int result = service.idcheck(inputId);
	 *   
	 *   if(result > 0) {
	 *      return "NNNNN";
	 *   } else {
	 *      return "NNNNY"; 
	 *   }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	
	
}



