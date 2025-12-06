package com.kh.spring.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardListController {
//	
//	@Autowired
//	private BoardService service;
//	
//	@RequestMapping("/list.bo")
//	public String boardListView(@RequestParam(value="currentPage",defaultValue="1") int currentPage, Model model) {
//		
//		//가지고 있는 총 게시글 개수
//		int listCount = service.listCount(); // 이거 데이터베이스에서 COUNT(*) 참고
//		
//		//한 페이지에서 보여질 개수(글이 16개라 5개로 지정)
//		int boardLimit = 5;
//		
//		//페이징바를 몇개까지 보여줄지 개수
//		//아래 페이징바를 몇개씩 끊어서 보여줄지를 말하는 것.
//		int pageLimit = 10; // 어차피 글이 16개고 한 페이지당 5개만 보여줘서 리미트는 10개를 걸었지만 보여지는 페이지수는 4개
//		
//		//PageInfo 받아오기
//		PageInfo pi = pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit);
//		
//		
////		//계산 처리시 보여질 끝 페이지 수 (endPage보다 작을 수 있음) 
////		int maxPage = 0;
////		// 예를 들어서, 페이징바 1개당 볼수있는 게시글 개수가 5개라고 가정..
////		// 글을 16개 올렸다고 가정하자. 16개의 글은 페이징바가 4개 까지 필요함. (그럼 이게 maxPage다)
////		// 페이징바 리미트를 10개까지 걸었으니 사실 5~10번째는 필요가 없다.
////		// 그래서 maxPage로 최대 페이징바 어디까지 나타낼지 파악해야한다.
////		// 게시글 개수에 따른 최대 페이지를 어디까지 나타내는가?
////		// 마지막 페이지
////		
////		/*
////		 * maxPage는 listCount와 boardLimit의 영향을 받는 수
////		 * 
////		 * 총 개수 / 보여질 개수
////		 * 
////		 * 16 / 5 -> 세페이지는 꽉 채우고 마지막 한 페이지는 1개가나옴. 무조건 올림 처리 해야한다. 페이지수는 4개.
////		 * 
////		 * listCount / boardLimit 를 double 계산 처리 후 올림 처리 (ceil)
////		 * 
////		 * 좀 더 쉽게 말하면 게시글 개수에 따른 최대 페이징 바
////		 */
////		
////		maxPage = (int) Math.ceil((double)listCount/boardLimit);
////		
////		//페이징 처리시 보여질 시작 페이지 수
////		int startPage = 0; 
////		/*
////		 * startPage는 currentPage와 pageLimit에 영향을 받는 수
////		 * pageLimit = 10 인 경우
////		 * 
////		 * currentPage는 아래 페이징 바에서 현재 몇 페이지에 속하는지를 나타냄.
////		 * 
////		 * currentPage가 1~10까지일땐 startPage는 1
////		 * currentPage가 11~20까지일땐 startPage는 11
////		 * currentPage가 21~30까지일땐 startPage는 21
////		 * 
////		 */
////		
////		startPage = (currentPage-1)/pageLimit * pageLimit + 1; 
////		//(currentPage-1), pageLimit는 서로 int형이니까 나눗셈을 하면 소수점이 없어짐.
////		
////		/*
////		 * 페이징 처리시 보여질 끝 페이지 수
////		 * 
////		 * endPage는 startPage와 pageLimit에 영향을 받는 수 
////		 */
////		
////		int endPage = startPage+pageLimit-1; 
////		//페이징바에서 무조건 끝 페이징 바 값을 의미함.
////		//그럼 당연히 maxPage와는 다른 개념 
////		
////		//생각을 해보면 endPage는 어떻게든 10개가 다 표현된다.
////		//그런데 maxPage는 게시글 개수에 따른 나타낼수 있는 최대 페이지수
////		
////		//최대페이지(maxPage)가 endPage수보다 작을때 endPage를 maxPage수로 변경하기
////		if(maxPage < endPage) {
////			endPage = maxPage; // maxPage에 있는 값을 endPage에 대입하기
////		}
////		
////		//페이징처리에 필요한 값들을 담아 전달할 수 있는 객체 준비하기
////		//이건 받아올수 없으므로 직접 작성
////		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage);
//		
//		/*
//		 * 1. Model - "딱 그 요청(request) 동안만 쓰는 데이터 바구니"
//		 * 
//		 * 특징 요약
//		 * 
//		 * 요청(request) -> 컨트롤러 -> JSP(View) 딱 이 과정에서만 살아있음
//		 * 응답 끝나면 바로 사라짐.
//		 * JSP에서 ${} 같은 걸로 꺼내서 화면에 뿌리는 용도
//		 * 브라우저가 페이지 이동(redirect)하면 Model 데이터는 날아감
//		 * 
//		 * 2. HttpSession - "브라우저가 켜져 있는 동안 계속 살아있는 저장 공간"
//		 * 
//		 * 특징 요약
//		 * 
//		 * 로그인한 유저처럼 계속 유지해야 하는 정보 저장
//		 * 브라우저가 닫히거나 세션이 만료될 때까지 살아있음
//		 * 여러 요청(request) 사이에서도 계속 유지됨
//		 * 스프링이 아니라 서버가 관리하는 저장소 
//		 * 
//		 * 언제 쓰는가?
//		 * 
//		 * - 로그인 상태 유지
//		 * - 장바구니
//		 * - 사용자 정보 유지 
//		 */
//		
//		//게시글 목록 조회
//		ArrayList<Board> list = service.boardListView(pi);
//		
//		model.addAttribute("pi",pi);
//		model.addAttribute("list",list); 
//		
//		
//				
//		
//		return "board/boardListView"; 
//	}
//	
//	//검색 메소드
//	@RequestMapping("/search.bo")
//	public String searchList(String condition, String keyword,Model model, @RequestParam(value="currentPage",defaultValue="1") int currentPage) {
//		
//		System.out.println(condition);
//		System.out.println(keyword);
//		
//		//전달받은 키워드와 카테고리를 map에 담아가기
//		//페이징 처리에 필요한 데이터 준비해서 PageInfo에 담아주고 페이징 처리하기 
//		//searchListCount() - 게시글 개수 조회
//		//기존 작성한 searchList의 매개변수가 변경되어야한다(pi가 추가 전달되어야함)
//		//rowbounds 객체 이용해서 페이징 처리후 jsp페이지에서도 pi 전달해서 페이징바 만들기 
//		//검색창에 선택했던 condition과 keyword가 유지되도록 처리해보기
//		
//		HashMap<String,String> map = new HashMap<>();
//		
//		map.put("condition", condition);
//		map.put("keyword", keyword);
//		
//        
//		
//		int listCount = service.searchListCount(map);  // 게시글 개수를 조회 
//		
//		//ArrayList<Board> searchList = service.searchList(map);
//		
//		int boardLimit = 5; // 한 페이지에 몇개가 들어가는가?
//		int pageLimit = 10;  // 페이징바를 몇개를 보여줄것인가?
//		
//		PageInfo pi = pagination.getPageInfo(listCount, currentPage, boardLimit, pageLimit);
//		
//		ArrayList<Board> searchList = service.searchList(pi);
//		
//	
//		model.addAttribute("map",map);
//		model.addAttribute("list",searchList); // pageinfo 정보도 전달하기
//		model.addAttribute("pi",pi); //검색 정보도 전달하기
//		
//		
//		 
//	    
//		
//		for(Board b : searchList) {
//			System.out.println(b);
//			
//		}
//		
//		
//		return "board/boardListView";
//	}

}
