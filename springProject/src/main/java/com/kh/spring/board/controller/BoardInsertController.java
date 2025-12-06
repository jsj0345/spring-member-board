package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

@Controller
public class BoardInsertController {
//	
//	@Autowired
//	private BoardService service;
//	
//	/*
//	 * @RequestMapping으로는 get방식과 post방식이 구분이 안되니까 아래와 같이 작성해보자. 
//	 * 
//	 * 같은 매핑 주소를 쓰고싶다? (Get, Post를 구분 지어야한다.) 
//	 * 
//	 * 중복되는 매핑주소는 쓸 수 없다. (@RequestMapping) 
//	 * 
//	 * @GetMapping("/insert.bo")
//	 * public String boardEnroll() {
//	 *    return "board/boardEnrollForm";
//	 * }
//	 * 
//	 * @PostMapping("/insert.bo")
//	 * public String boardInsert(Board board) {
//	 *    
//	 *    return "redirect:list.bo"; 
//	 * 
//	 * }
//	 */
//	
//	
//	//글작성 페이지로 이동 메소드
//	@RequestMapping("/enroll.bo")
//	public String enroll() {
//		return "board/boardEnrollForm";
//	}
//	
//	//제목, 작성자, 첨부파일, 내용 (글작성 요청 메소드)
//	@RequestMapping("/insert.bo")  // pom.xml, servlet-content.xml에서 MultipartFile 확인
//	public String boardInsert(Board board,MultipartFile uploadFile,HttpSession session) {
//		
//		/*
//		 * 첨부파일 처리를 하려면 form 요청 인코딩 방식이 multipart/form-data로 설정되어야한다.
//		 * 이때 기존 요청과는 형식이 달라졌기 때문에 그에 맞는 데이터 형식을 사용해야하는데
//		 * multipart 요청을 처리하기 위해선 MultipartResolver가 필요하다.
//		 * formdata 형식을 파싱해서 MultipartFile 형태로 처리해주는 도구
//		 * 
//		 * pom.xml에 commons-fileupload 와 commons-io 두 dependency를 추가한 뒤
//		 * 업로드 처리를 위한 MultipartResolver를 servlet-context에 등록된다. 
//		 */
//		
//		//첨부파일이 전달 되었는지 확인 하고 
//		//전달된 파일이 있으면 서버에 업로드 처리 및 데이터베이스에 등록할 데이터 처리하기
//		//전달된 파일이 없어도 게시글 정보는 등록되어야 하니 해당 부분 조건 처리하기
//		
//		//첨부파일이 전달되었는지 확인하는 방법 : MultipartFile 객체에서 파일명 추출해보기
//		//첨부파일이 전달되지 않아도 MultipartFile 객체는 주입되기 때문에 null값 비교가 아닌 (스프링에서 아 필요로 하는구나 하고 객체를 주입함)
//		//전달된 파일명이 있는지 확인해보는것
//		//파일명 추출 메소드 : getOriginnalFileName()
//		
//		/*
//		 * MultipartFile은 빈으로 등록되어 있으므로 스프링에선 필요하구나 라고 생각하고 객체를 주입해준다. 
//		 * 그래서 평소에 코드를 짤때는 null인지 아닌지 비교를 했으면 됐는데 파일 객체는 있으므로 이름으로 비교해야한다. 
//		 */
//		
//		System.out.println(board);
//		System.out.println(uploadFile); //field를 보면 uploadFile이라고 주어짐 
//		System.out.println(uploadFile.getOriginalFilename());
//		
//		//전달된 파일명이 비어있지 않은 경우 (파일이 있는 경우 처리)
//		if(!uploadFile.getOriginalFilename().equals("")) {
//			//파일 저장 처리 및 데이터베이스에 등록할 원본 파일명과 변경된 파일명 처리하기
//			
//			//저장 경로 설정 및 파일명 변경작업 수행(메소드 만들어준것으로 사용)
//			String changeName = saveFile(session,uploadFile);
//			
//			//Board 객체에 변경이름이랑 원본이름 넣어주기
//			board.setOriginName(uploadFile.getOriginalFilename());
//			board.setChangeName("/resources/uploadFiles/" + changeName);
//			
//			//파일 저장 경로는 webapp에 처리해야하는데 이때 이 경로를 찾아올 수 있는 메소드 확인해보기
//			//webapp에 resources/uploadFiles 폴더로 경로 잡기 
////			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
//			
////			System.out.println(savePath);
////			
////			savePath += uploadFile.getOriginalFilename(); 
//			
//			//서버에 등록할때 파일명이 달라야함. 구분되어야한다. 왜냐면 파일이름이 같으면 덮어쓰기가 되서 문제 발생한다. 
//			
//			
//			
//		}
//		
//		
//		
//		int result = service.boardInsert(board);
//		
//		//글이 작성 됐을 경우와 안 됐을 경우를 따져야한다. 
//		
//		if(result>0) {
//			session.setAttribute("board",board); 
//			session.setAttribute("alertMsg", "글 작성 완료!");
//			return "redirect:/list.bo"; //글을 작성하고나서 최신 글이니까 첫 페이지로 돌아가야한다.	
//		} else {
//			session.setAttribute("alertMsg","글 작성 실패!");
//			return "board/boardEnrollForm"; 
//		}
//		
//		
//		
//		
//		  
//	}
//	
//	//파일 업로드시 처리할 메소드 
//	public String saveFile(HttpSession session, MultipartFile uploadFile) {
//		//1. 원본 파일명 추출
//		String originName = uploadFile.getOriginalFilename();
//		
//		//2.시간형식 문자열로 뽑아주기
//		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		
//		//3.랜덤값 5자리 추출
//		int ranNum = (int)(Math.random()*90000+10000);
//		
//		//4.원본 파일에서 확장자 추출
//		String ext = originName.substring(originName.lastIndexOf("."));
//		
//		//5.합쳐주기
//		String changeName = currentTime+ranNum+ext;
//		
//		//6.서버에 업로드 처리경로
//		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
//		
//		//7.서버에 업로드 처리
//		//MultipartFile의 transferto 메소드 사용
//		try {
//			uploadFile.transferTo(new File(savePath+changeName));
//		}  catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//8. 변경된 이름 반환하기
//		return changeName; 
//		
//	}

}
