
package com.kh.spring.board.controller;

import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

@Controller
public class BoardUpdateController {
//	
//	@Autowired
//	private BoardService service;
//	
//	@PostMapping("/update.bo") //원래 post였음
//	public String boardUpdate(int bno,Model model,HttpSession session) {
//		
//		Board board = service.boardDetail(bno); // 게시글 상세조회
//		
//		//게시글 수정하기 위해서 게시글 정보 조회하여 모델에 담기
//		model.addAttribute(board);
//	
//		return "board/boardUpdateForm"; 
//	}
//
//	@PostMapping("/boardUpdate.bo") // 제목, 작성자, 파일, 내용 
//	public String updateBoard(HttpSession session, Board board, MultipartFile uploadFile) {
//		
//		/*
//		 * 정보수정 성공시 성공메시지와 함께 새로운 첨부파일이 있고 기존에 첨부파일이 있었다면 해당 파일 삭제 후 디테일뷰로 재요청
//		 * 정보수정 실패시 실패 메시지와 함께 디테일뷰로 재요청
//		 * 
//		 * 새로운 첨부파일이 있는 경우 - 데이터베이스에 변경된 데이터 적용 + 서버에 업로드된 기존 첨부파일 삭제
//		 * 새로운 첨부파일이 없는 경우 - 데이터베이스에 변경된 데이터 적용
//		 * 게시글 등록 기능에서 사용했던 첨부파일 처리 참고해서 진행할것
//		 * 
//		 * 첨부파일 삭제하는 방법 - 실제 파일저장경로찾아서 해당 파일을 파일객체에 연결하여 .remove() 메소드 사용
//		 */
//		
//		String deleteFile = null; 
//		
//		//새로운 첨부파일이 전달되었는지 확인 
//		if(!uploadFile.getOriginalFilename().equals("")) {
//			
//			//기존 첨부파일이 있는지 없는지 확인 (만약 새 첨부파일이 있고 기존 첨부파일도 있었으면 기존첨부파일을 지워야한다.)
//			if(board.getOriginName()!=null) { // 이 조건 이해 해야함. 
//				deleteFile = board.getChangeName(); // 서버에저장된 경로(변경된 파일명) 추후 삭제처리용 
//			}
//			
//			//새로 업로드된 파일 서버에 업로드 및 변경이름 받아오기
//			String changeName = saveFile(session,uploadFile);
//			
//			//업로드 되었다면 데이터베이스에 등록할 이름들 board객체에 넣어주기
//			board.setOriginName(uploadFile.getOriginalFilename()); //원본파일명
//			board.setChangeName("/resources/uploadFiles/"+changeName); //저장경로 담기 
//			
//		}
//		
//		int result = service.boardUpdate(board);
//		
//		if(result > 0) { //정보수정 성공
//			session.setAttribute("alertMsg", "정보 수정 성공!");
//			
//			//기존 첨부파일이 있었다면 삭제하기
//			if(deleteFile!=null) { //기존 파일경로가 저장되어있다면
//			   //파일 객체로 해당 경로 잡아서 삭제처리하기
//			   String path = session.getServletContext().getRealPath(deleteFile);
//			   
//			   //실제 파일 저장경로로 파일 연결 
//			   new File(path).delete();
//				
//			}
//		} else {
//			session.setAttribute("alertMsg", "정보 수정 실패!");
//		}
//		
//		return "redirect:/detail.bo?bno="+board.getBoardNo(); 
//	    
//	}
//	
//	
//	@PostMapping("/delete.bo")
//	public String deleteBoard(Board board, String filePath, HttpSession session) {
//		
//		String deleteFile = null; 
//		
//		if(board.getOriginName()!=null) {
//			deleteFile = board.getChangeName(); // 데이터베이스에서 삭제하기 전에 이름 보관
//			System.out.println(deleteFile);
//		}
//		
//		int result = service.deleteBoard(board);
//	
//		
//		if(result > 0) {
//			  if(deleteFile!=null) {
//				  
//				  	String path = session.getServletContext().getRealPath(deleteFile); // 실제 경로를 찾기 
//					
//					System.out.println(path);
//					
//					new File(path).delete(); 
//				  
//			  }
//			
//			
//			session.setAttribute("alertMsg", "글 삭제 성공!");
//			
//				
//		} else {
//			
//			session.setAttribute("alertMsg", "글 삭제 실패!");
//			
//		}
//		
//		return "redirect:/list.bo"; 
//		
//	}
//	
//	
//	/*
//	@PostMapping("/delete.bo")
//	public String deleteBoard(int bno, String filePath, HttpSession session) {
//		
//		//게시글 삭제 요청
//		int result = service.deleteBoard(bno);
//		
//		if(result>0) { //데이터베이스에서 삭제처리 성공
//			
//			//전달받은 파일 경로로 해당 파일 삭제하기
//			session.setAttribute("alertMsg", "게시글이 삭제되었습니다.");
//			
//			//파일삭제
//			new File(session.getServletContext().getRealPath(filePath)).delete(); 
//			
//			return "redirect:/list.bo"; //게시글이 삭제되었으니 목록으로 보내기 
//			
//		} else { // 실패
//			session.setAttribute("alertMsg", "게시글 삭제 실패");
//			
//			return "redirect:/detail.bo?bno="+bno; 
//			
//		}
//	}
//	*/
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
