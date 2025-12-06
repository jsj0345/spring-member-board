package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	
	//게시글 총 개수 
	int listCount();
	
	//게시글 목록 조회
	ArrayList<Board> boardList(PageInfo pi);

	//조회수증가
	int increaseCount(int bno);
	//상세보기
	Board boardDetail(int bno);
	
	//게시글 등록
	int boardInsert(Board b);

	//게시글 수정
	int updateBoard(Board b);

	//게시글 삭제
	int deleteBoard(int bno);
	
	//게시글 검색
	ArrayList<Board> searchList(HashMap<String, String> map, PageInfo pi);
	
	//검색 게시글 개수
	int searchListCount(HashMap<String, String> map);
	

}




/*
package com.kh.spring.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {
	
	int listCount(); 
	
	ArrayList<Board> boardList(PageInfo pi); 
	
	Board boardDetail(int bno);

	int increaseCount(int bno);

	int boardInsert(Board board);

	int boardUpdate(Board board);

	int deleteBoard(Board board);

	ArrayList<Board> searchList(HashMap<String, String> map);

	int searchListCount(HashMap<String, String> map);

	ArrayList<Board> searchList(PageInfo pi); 
	
	

}
*/
