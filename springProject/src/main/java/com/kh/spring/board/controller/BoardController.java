package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller // 컨트롤러 어노테이션은 안에 컴포넌트가 있어서 컴포넌트 스캔이 자동으로된다. 
public class BoardController {
	
	
	@Autowired
	private BoardService service; // 필드 주입 
	
	
	@RequestMapping("/list.bo")
	public String boardList(@RequestParam(value="page",defaultValue = "1")
						 	int currentPage
						   ,Model model) {
		//쿼리스트링에 page가 없으면 자동으로 1을 넣고 page값이 있으면 그 값을 그대로 사용함.
		//이렇게 하는 이유는 자유게시판으로 이동했을때 첫 페이지를 1로 두기위함. 
		
		
		//게시글 목록 
		//페이징처리에 필요한 요소들 
		//현재페이지값 : currentPage
		//게시글 총 개수 조회해오기 
		int listCount = service.listCount();
		int boardLimit = 5; //게시글 보여줄 개수
		int pageLimit = 10; //페이징바 개수
		
		
		//PageInfo 받아오기 
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit);
		
		ArrayList<Board> list = service.boardList(pi);
		
		//위임할때 데이터 담아서 전달하기
		model.addAttribute("pi",pi);
		model.addAttribute("list",list);
		
		return "board/boardListView";
	}
	
	/*
	@RequestMapping("/list.bo")
	public String boardList(@RequestParam(value="page",defaultValue="1") int currentPage,Model model) {
	    
	    //게시글 목록
	    //페이징 처리에 필요한 요소들
	    //현재페이지값 : currentPage
	    //게시글 총 개수 조회해오기
	    int listCount = service.listCount(); 
	    int boardLimit = 5; // 한 페이지에 몇개의 게시글을 보여줄지를 정한것
	    int pageLimit = 10; // 페이징바가 몇개를 보이게 할지를 정한것
	    
	    //PageInfo를 받아오기
	    PageInfo pi = Pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit); 
	    
	    위에서 최대 몇 페이지를 보이게 할지를 정하는 maxPage와 페이징바에서 맨왼쪽 페이징바를 몇으로 잡을지 맨오른쪽 페이징바를 몇으로 잡을지를 계산하는것
	    
	    ArrayList<Board> list = service.boardList(pi); //페이징 처리에 필요한 정보를 서비스에 전달해서 데이터 베이스에 있는 글들을 담아오기
	    
	    //위임할때 데이터 담아서 전달하기
	    model.addAttribute("pi",pi);
	    model.addAttribute("list",list);
	    
	    //모델은 뷰 페이지로 이동할때 데이터를 일시적으로 담음 그리고 요청이 끝나면 데이터가 없어짐 (한마디로 뷰 페이지에 전달될때만 데이터를 일시적으로 볼 수 있음)
	    
	    return "board/boardListView"; 
	    
	}
	 
	*/
	
	
	@RequestMapping("/detail.bo")
	public String boardDetail(int bno
							 ,HttpSession session
							 ,HttpServletRequest request
							 ,Model model) {
		
		//쿼리스트링에 있는 key값의 이름을 매개변수이름과 똑같이 두면 value를 갖고온다. 
		
		//조회수 증가처리 
		int result = service.increaseCount(bno);
		
		if(result>0) { //조회수 증가 성공
			//게시글 조회
			Board b = service.boardDetail(bno);
			
			
			model.addAttribute("b",b); //게시글 정보 담아주기 (위임)
			
			return "board/boardDetailView";
		}else{ //조회수 증가 실패 
			session.setAttribute("alertMsg", "게시글 조회 실패");

			return "redirect:"+request.getHeader("referer");//이전 경로로
		}
	}
	
	/*
	@RequestMapping("/detail.bo") 
	public String boardDetail(int bno,HttpSession session,HttpServletRequest request,Model model) {
	   //쿼리스트링에 있는 key값의 이름을 매개변수 이름과 똑같이 두면 value를 갖고온다.
	     
	   //조회수 증가 처리
	   int result = service.increaseCount(bno); 
	   
	   if(result>0) { //조회수 증가 성공
	       //게시글 조회
	       Board b = service.boardDetail(bno); 
	       
	       model.addAttribute("b",b); // 게시글 정보 담아주기 (위임)
	    
	       return "board/boardDetailView"; 
	   } else { // 조회수 증가 실패
	       session.setAttribute("alertMsg","게시글 조회 실패!");
	       
	       return "redirect:"+request.getHeader("referer"); // 이전 경로로
	   }    
	      
	}
	*/
	
	//글작성 페이지로 이동 메소드
	//@RequestMapping(value = "/insert.bo",method = RequestMethod.GET)
	@GetMapping("/insert.bo")
	public String boardEnroll() {
		
		return "board/boardEnrollForm";
	}
	
	/*
	//글작성 페이지로 이동 메소드
	//@RequestMapping(value = "/insert.bo", method = RequestMethod.GET)
	@GetMapping("/insert.bo")
	public String boardEnroll() {
	    return "board/boardEnrollForm"; 
	}
	*/
	
	//글작성 요청 
	//@RequestMapping("/insert.bo")
	@PostMapping("/insert.bo")
	public String boardInsert(Board b
							 ,MultipartFile uploadFile
							 ,HttpSession session) {
		/*
		 * 첨부파일 처리를 하려면 form 요청 인코딩 방식이 multipart/form-data로 설정되어야한다.
		 * 이때 기존 요청과는 형식이 달라졌기 때문에 그에 맞는 데이터 형식을 사용해야하는데
		 * multipart 요청을 처리하기 위해선 MultipartResolver가 필요하다.
		 * formdata 형식을 파싱해서 MultipartFile 형태로 처리해주는 도구
		 * 
		 * pom.xml에 commons-fileupload 와 commons-io 두 dependency를 추가한 뒤 
		 * 업로드 처리를 위한 MultipartResolver를 servlet-context에 등록한다.
		 * */
		
		//첨부파일이 전달 되었는지 확인하고 
		//전달된 파일이 있으면 서버에 업로드 처리 및 데이터베이스에 등록할 데이터 처리하기 
		//전달된 파일이 없어도 게시글 정보는 등록되어야하니 해당 부분 조건 처리하기 
		
		//첨부파일이 전달되었는지 확인하는 방법 : MultipartFile 객체에서 파일명 추출해보기
		//첨부파일이 전달되지 않아도 MultipartFile 객체는 주입되기 때문에 null값 비교가 아닌 
		//전달된 파일명이 있는지 확인해보는것
		//파일명 추출 메소드 : getOriginalFileName()
		//전달된 파일명이 비어있지 않은 경우(파일이 있는 경우 처리)
		
		//System.out.println(uploadFile); 
		
		/*
		 * 첨부파일(uploadFile)이 없어도
		 * MultipartFile 객체는 주입해줘서 null값으로 비교하지말고 파일 이름과 비교해야한다. 
		 * 
		 * 실제로 파일이름이 없을때 출력결과가 다음과 같이 나온다. 
		 * MultipartFile[field="uploadFile", filename=, contentType=application/octet-stream, size=0]
		 * 
		 * 그래서 filename과 비교하자. 
		 */
		
		//System.out.println(b);
		
		if(!uploadFile.getOriginalFilename().equals("")) {
			//파일 저장 처리 및 데이터베이스에 등록할 원본 파일명과 변경된 파일명 처리하기
			//변경된 파일명을 둬야한다. 안두면 기존 파일을 덮어써서 문제가 발생한다.
			
			//저장 경로 설정 및 파일명 변경작업 수행(메소드 만들어준것으로 사용)
			String changeName = saveFile(session,uploadFile);
			
			//Board객체에 변경이름이랑 원본이름 넣어주기 
			b.setOriginName(uploadFile.getOriginalFilename()); //원래 파일이름 
			b.setChangeName("/resources/uploadFiles/"+changeName); //파일 변경 이름
			//서버에 저장된 파일 
			
			System.out.println(b.getOriginName());
			System.out.println(b.getChangeName());
			
		}
		
		/*
		 * ✅ “첨부파일을 업로드할 때 서버 내부의 실제 경로에 파일을 저장해야 다운로드가 가능하다.”
		 * 다운로드란 결국 서버에 존재하는 파일을 브라우저가 다시 가져가는 과정임.
		 * 근데 서버에서 파일을 실제로 보관하는 경로는 보통은 프로젝트명을 컨텍스트 루트로 지정하지만
		 * 이 프로젝트는 다른 이름으로 컨텍스트 루트를 지정했음.
		 * 
		 * 그래서 컨텍스트 루트/src/main/webapp/resources/uploadFiles/바뀐 파일명 이렇게 있는데
		 * 
		 * src/main/webapp은 디폴트다.
		 * 
		 * 그래서 실제 서버에서 파일을 연결할땐 컨텍스트 루트/바뀐 파일명이다.
		 * 
		 * 왜냐면 바뀐 파일명엔 String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		 * 
		 *
		 */
		
		//게시글 등록처리
		int result = service.boardInsert(b);
		
		if(result>0) {
			session.setAttribute("alertMsg", "게시글 등록성공!");
		}else {
			session.setAttribute("alertMsg", "게시글 등록실패!");
		}
		
		return "redirect:list.bo";
		
	}
	
	/*
	//글작성 요청
	@PostMapping("/insert.bo")
	public String boardInsert(Board b, MultipartFile uploadFile, HttpSession session) {
	    
	   첨부파일 처리를 하려면 form 요청 인코딩 방식이 multipart/form-data로 설정되어야한다.
	   이때 기존 요청과는 형식이 달라졌기 때문에 그에 맞는 데이터 형식을 사용해야하는데
	   multipart 요청을 처리하기 위해선 MultipartResolver가 필요하다.
	   formdata 형식을 파싱해서 MultipartFile 형태로 처리해주는 도구
	   
	   pom.xml에 commons-fileupload와 commons-io 두 dependency를 추가한 뒤
	   업로드 처리를 위한 MultipartResolver를 servlet-context에 등록한다.
	
	   첨부파일이 전달 되었는지 확인하고
	   전달된 파일이 있으면 서버에 업로드 처리 및 데이터 베이스에 등록할 데이터 처리하기
	   전달된 파일이 없어도 게시글 정보는 등록되어야하니 해당 부분 조건 처리하기
	   
	   첨부파일이 전달되었는지 확인하는 방법 : MulfipartFile 객체에서 파일명 추출해보기
	   첨부파일이 전달되지 않아도 MultipartFile 객체는 주입되기 때문에 null값 비교가 아닌
	   전달된 파일명이 있는지 확인해보는것
	   파일명 추출 메소드 : getOriginalFileName()
	   전달된 파일명이 비어있지 않은 경우 (파일이 있는 경우 처리) 
	   
	   system.out.println(uploadFile); 
	   
	   if(!uploadFile.getOriginalFilename().equals("") {
	      //파일 저장 처리 및 데이터베이스에 등록할 원본 파일명과 변경된 파일명 처리하기
	      변경된 파일명을 둬야한다. 안두면 기존 파일을 덮어써서 문제가 발생한다.
	      
	      //저장 경로 설정 및 파일명 변경작업 수행(메소드 만들어준것으로 사용)
	      String changeName = saveFile(session,uploadFile);
	      
	      //Board객체에 변경이름이랑 원본이름 넣어주기
	      b.setOriginName(uploadFile.getOriginalFilename());
	      b.setChangeName("/resources/uploadFiles/" + changeName); 
	   }
	   
	   //게시글 등록처리
	   int result = service.boardInsert(b);
	   
	   if(result>0) {
	      session.setAttribute("alertMsg","게시글 등록성공!");
	   } else {
	      session.setAttribute("alertMsg","게시글 등록실패!");
	   }
	   
	   return "return:list.bo";
	   
	}
	*/
	
	
	//파일 업로드시 처리할 메소드 
	public String saveFile(HttpSession session,MultipartFile uploadFile) {
		//1.원본 파일명 추출
		String originName = uploadFile.getOriginalFilename();
		
		//2.시간형식 문자열로 뽑아주기 
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//3.랜덤값 5자리 추출
		int ranNum = (int)(Math.random()*90000+10000);
		
		
		//4.원본파일에서 확장자 추출 (마지막 . 기준으로 잘라내기)
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//5.합쳐주기 
		String changeName = currentTime+ranNum+ext; // 이제 기존에 파일 덮어쓰면 안되니까 이름을 구분지려고 쓰는 용도. 
		
		//6.서버에 업로드 처리경로 
		//파일 저장 경로는 webapp에 처리해야하는데 이때 이 경로를 찾아올 수 있는 메소드 확인해보기
		//webapp에 resources/uploadFiles 폴더로 경로 잡기 
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // 파일 저장되는 실제 경로
		
		//session.getServletContext()가 컨텍스트 루트/src/main/webapp이 디폴트로 깔려있는거다. 그래서 그아래만 추가하면된다. 
		//그래서 savePath는 사실상 컨텍스트루트/src/main/webapp/resources/uploadFiles/까지 있는거고
		//그뒤에 원래 파일명이 아닌 바뀐 파일명을 넣은 것이다. 
		
		System.out.println(savePath);
		
		//7.서버에 업로드 처리 
		//MultipartFile의 transferto 메소드 사용
		try {
			uploadFile.transferTo(new File(savePath+changeName)); // 파일 저장 
			//파일을 저장할땐 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//8.변경된 이름 반환하기
		return changeName;
		
	}
	
	/*
	파일 업로드시 처리할 메소드
	public String saveFile(HttpSession session,MultipartFile uploadFile) {
	
	     //1.원본 파일명 추출
	     String originName = uploadFile.getOriginalFilename();
	     
	     //2.시간형식 문자열로 뽑아주기
	     String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	     
	     //3.랜덤값 5자리 추출
	     int ranNum = (int)(Math.random()*90000+10000);
	     
	     //4.원본파일에서 확장자 추출 (마지막 . 기준으로 잘라내기)
	     String ext = originName.substring(originName.lastIndexOf("."));
	     
	     //5.합쳐주기 
	     String changeName = currentTime+ranNum+ext;
	     
	     //6.서버에 업로드 처리경로
	     //파일 저장 경로는 webapp에 처리해야하는데 이때 이 경로를 찾아올 수 있는 메소드 확인해보기
	     //webapp에 resources/uploadFiles 폴더로 경로 잡기
	     String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
	     
	     //7.서버에 업로드 처리
	     //MultipartFile의 transferto 메소드 사용
	     try {
	           uploadFile.transferTo(new File(savePath + changeName));
	     } catch (IOException e) {
	           e.printStackTrace();
	     }
	     
	      
	     //8.변경된 이름 반환하기
	     return changeName;
	}      
	     
	     
	*/
	
	/*
	    정리
	    
	    String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); 
	    
	    컨텍스트루트/src/main/webapp -> session.getServletContext
	    
	    getRealPath안에 있는 경로까지 붙이면 완성. (컨텍스트루트/src/main/webapp/resources/uploadFiles/)
	    
	    바뀐 파일 명은 /resources/uploadFiles + (현재 날짜및 시간 + 랜덤숫자 + 확장자)
	    
	    근데 이거는 개인 pc에 저장되면서도 서버가 컨텍스트 루트에 바뀐 파일명만 추가하면 접근할수있음. 
	    
	    (src/main/webapp)은 디폴트니까. 
	*/
	
	
	@PostMapping("/update.bo")
	public String boardUpdate(int bno,Model model) {
		
		Board b = service.boardDetail(bno); //게시글 상세조회
		
		//모델은 요청을 하고나서 그 요청때만 값이 살아있음. 그래서 새로 받아오고나서 요청을 해야함.
		
		//그러면 jsp파일에서도 값을 볼 수 있다. 
		
		//게시글 수정하기위해서 게시글 정보 조회하여 모델에 담기
		model.addAttribute("b",b);
		
		return "board/boardUpdateForm";
	}
	
	/*
	@PostMapping("/update.bo")
	public String boardUpdate(int bno,Model model) {
	    
	    Board b = service.boardDetail(bno); // 게시글 상세조회
	    
	    //게시글 수정하기 위해서 게시글 정보 조회하여 모델에 담기
	    model.addAttribute("b",b);
	    
	    return "board/boardUpdateForm";
	}    
	*/
	
	
	@PostMapping("/boardUpdate.bo")
	public String updateBoard(Board b
							 ,MultipartFile uploadFile
							 ,HttpSession session) {
		/*
		 * 정보수정 성공시 성공메시지와 함께 새로운 첨부파일이 있고 기존에 첨부파일이 있었다면 해당 파일 삭제 후 디테일뷰로 재요청
		 * 정보수정 실패시 실패 메시지와 함께 디테일뷰로 재요청
		 * 
		 * 새로운 첨부파일이 있는 경우 - 데이터베이스에 변경된 데이터 적용 + 서버에 업로드된 기존 첨부파일 삭제 
		 * 새로운 첨부파일이 없는 경우 - 데이터베이스에 변경된 데이터 적용 
		 * 게시글 등록 기능에서 사용했던 첨부파일 처리 참고해서 진행할것
		 * 
		 * 첨부파일 삭제하는 방법 - 실제 파일저장경로찾아서 해당 파일을 파일객체에 연결하여 .delete() 메소드 사용
		 * */
		
		/*
		 * 기존 첨부파일이 있는데 새로운 첨부파일은 없으면 그냥 냅두기
		 */
		
		String deleteFile = null;
		//새로운 첨부파일이 전달되었는지 확인하기 
		if(!uploadFile.getOriginalFilename().equals("")) {
			
			//기존 첨부파일이 있는지 없는지 확인 (만약 새 첨부파일이 있고 기존 첨부파일도 있었으면 기존첨부파일을 지워야한다.)
			if(b.getOriginName()!=null) { 
				deleteFile = b.getChangeName();//서버에 저장된 경로(변경된 파일명) 추후 삭제처리용
			}
			
			//새로 업로드된 파일 서버에 업로드 및 변경이름 받아오기 
			String changeName = saveFile(session,uploadFile);
			
			//업로드 되었다면 데이터베이스에 등록할 이름들 board객체에 넣어주기 
			b.setOriginName(uploadFile.getOriginalFilename()); //원본파일명
			b.setChangeName("/resources/uploadFiles/"+changeName);//저장경로 담기
		}
			
		int result = service.updateBoard(b);
		
		if(result>0) {//정보수정 성공 
			session.setAttribute("alertMsg", "정보 수정 성공!");
			
			//기존 첨부파일이 있었다면 삭제하기 
			if(deleteFile!=null) {//기존 파일경로가 저장되어있다면
				//파일 객체로 해당 경로 잡아서 삭제처리하기 
				String path = session.getServletContext().getRealPath(deleteFile);
				
				//실제 파일 저장경로로 파일 연결
				new File(path).delete();
			}
			
		}else {//정보수정 실패
			session.setAttribute("alertMsg", "정보 수정 실패!");
		}
		
		return "redirect:/detail.bo?bno="+b.getBoardNo();//기존 상세페이지로 이동 (인풋태그가 hidden이라 갖고 올 수 있다.) 
	}
	
	@PostMapping("/delete.bo")
	public String boardDelete(Board b,HttpSession session,Model model) {

		   String deleteFile = null;

			if(b.getOriginName()!=null) { // 기존 첨부파일 명이 있으면 경로를 포함반 changeName으로 초기화를 해준다.
				  deleteFile = b.getChangeName();
				  
				  System.out.println(deleteFile);
			}

			int result = service.boardDelete(b.getBoardNo());

			if(result > 0) { // 삭제가 성공하면 데이터베이스엔 안 남는다.
				 String path = session.getServletContext().getRealPath(deleteFile);

				 new File(path).delete();
				 
				 session.setAttribute("alertMsg","게시글 삭제 성공!");
                 /*
				 model.addAttribute("alertMsg","게시글 삭제 성공!");
				 여기서 모델에 객체를 담으면 list.bo에 맞는 컨트롤러로 간다고 하더라도 또 다른 view페이지로 넘어가기
				 때문에 alertMsg의 정보가 없어진다. 따라서 session으로 담도록 한다. 
                 */
				 return "redirect:/list.bo"; // 이거 defaultValue가 1이라 현재페이지 1로 되는것. 

			} else {

				//model.addAttribute("alertMsg","게시글 삭제 실패!");
				session.setAttribute("alertMsg","게시글 삭제 실패!");

			    return "redirect:/detail.bo?bno="+b.getBoardNo();
			}
	}		

	
	
	/* 강사님 코드 
	@PostMapping("/delete.bo")
	public String deleteBoard(int bno,String filePath,HttpSession session) {
	
		//게시글 삭제 요청 
		int result = service.deleteBoard(bno);
		
		
		if(result>0) {//데이터베이스에서 삭제처리 성공
			
			//전달받은 파일 경로로 해당 파일 삭제하기 
			session.setAttribute("alertMsg", "게시글이 삭제되었습니다.");
			
			//파일삭제 
			new File(session.getServletContext().getRealPath(filePath)).delete();
			
			return "redirect:/list.bo";//게시글이 삭제되었으니 목록으로 보내기
			
		}else {//실패 
			session.setAttribute("alertMsg", "게시글 삭제 실패");
			
			return "redirect:/detail.bo?bno="+bno; //원래 페이지
		}
		
	}
	*/
	
	
	//검색 메소드
	@RequestMapping("/search.bo")
	public String searchList(@RequestParam(value="page",defaultValue = "1")
							 int currentPage
							,String condition
							,String keyword
							,Model model) {
		
		//전달받은 키워드와 카테고리를 map에 담아가기
		//페이징처리에 필요한 데이터 준비해서 PageInfo에 담아주고 페이징 처리하기 
		//searchListCount() - 게시글 개수 조회 
		//기존 작성한 searchList의 매개변수가 변경되어야한다(pi가 추가 전달되어야함)
		//rowbounds 객체 이용해서 페이징 처리후 jsp페이지에도 pi 전달해서 페이징바 만들기
		//검색창에 선택했던 condition과 keyword가 유지 되도록 처리해보기
		HashMap<String,String> map = new HashMap<>();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		//페이징처리를 위한 데이터들 준비 
		//조건에 맞는 검색 결과 개수를 찾아와야하기 때문에 map 전달 
		int listCount = service.searchListCount(map);
		int boardLimit = 5;
		int pageLimit = 10;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit);
		
		
		ArrayList<Board> searchList = service.searchList(map,pi);
		
		model.addAttribute("list",searchList);
		model.addAttribute("pi",pi);//pageinfo 정보도 전달하기 (페이징바 처리용)
		model.addAttribute("map",map);//검색 정보도 전달하기 (기존 검색 내용 유지용)
		
		
		return "board/boardListView";
	}
	
	/*
	@ResponseBody
	@RequestMapping(value="/selectList.re", produces="application/json;charset=UTF-8")
	public ArrayList<Reply> replyList() {
		
		ArrayList<Reply> replyList = service.replyList();
		
		
		return replyList; 
		
		
	}
	*/
	
	//댓글 목록 조회
	@ResponseBody
	@RequestMapping(value="/selectList.re", produces="application/json;charset=UTF-8")
	public List<Reply> replyList(int refBno) {
	   //참조 게시글 번호를 이용하여 해당 게시글에 작성된 댓글 목록 조회해오기
	   
	   List<Reply> list = service.replyList(refBno);
	   
	   //목록 반환
	   return list; 
	}
	
	//댓글 등록 컨트롤러
	@ResponseBody
	@RequestMapping(value="/insertReply.re", produces="text/plain;charset=UTF-8")
	public String insertReply(Reply reply) {
		int result = service.insertReply(reply);
		
		if(result > 0) {
			return "댓글 등록 성공!";
		} else {
			return "댓글 등록 실패!";
		}
	}
	
	//게시글 조회수 1~5순위 갖고오는 컨트롤러
	@ResponseBody
	@RequestMapping(value="/topList.bo",produces="application/json;charset=UTF-8")
	public ArrayList<Board> topList() {
		
		ArrayList<Board> boardList = service.topList(); //조회수가 높은 1~5순위 게시글 리스트 갖고오기
		
		//받을 데이터는 딱히 없으니 매개변수는 작성 할 필요가 없다.
		
		return boardList; 
		
	}
	
	
	/*
	@ResponseBody 
	@RequestMapping(value="/insertReply.re", produces="application/json;charset=UTF-8")
	public int insertReply(Reply reply) {
	   int result = service.insertReply(reply); 
	   
	   if(result > 0) {
	      return result; 
	   } else {
	      return result; 
	   }
	
	}  
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
