package com.kh.spring.board.model.service;

import java.util.ArrayList;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao dao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int listCount() {
		
		return dao.listCount(sqlSession);
	}
	
	@Override
	public ArrayList<Board> boardList(PageInfo pi) {
		
		return dao.boardList(sqlSession,pi);
	}
	
	
	@Override
	public int increaseCount(int bno) {
		
		return dao.increaseCount(sqlSession,bno);
	}

	@Override
	public Board boardDetail(int bno) {

		return dao.boardDetail(sqlSession,bno);
	}
	
	
	@Override
	public int boardInsert(Board b) {
	
		return dao.boardInsert(sqlSession,b);
	}
	
	@Override
	public int updateBoard(Board b) {
	
		return dao.updateBoard(sqlSession,b);
	}
	
	@Override
	public int deleteBoard(int bno) {
		
		return dao.deleteBoard(sqlSession,bno);
	}
	
	@Override
	public ArrayList<Board> searchList(HashMap<String, String> map,PageInfo pi) {
	
		return dao.searchList(sqlSession,map,pi);
	}
	
	@Override
	public int searchListCount(HashMap<String, String> map) {
	
		return dao.searchListCount(sqlSession,map);
	}
	
	
	
}

/*
@Service
public class BoardServiceImpl implements BoardService {

   @Autowired
   private BoardDao dao; // 저장소 객체를 스프링에서 자동으로 주입
   
   @Autowired
   private SqlSessionTemplate sqlSession; // sqlSessionTemplate 자동으로 주입 

   @Override
   public int listCount() {
   
     int result = dao.listCount(sqlSession);
     
     return result; 
   
   }
   
   @Override
   public ArrayList<Board> boardList(PageInfo pi) {
      ArrayList<Board> boardList = dao.boardList(sqlSession,pi);
      
      return boardList; 
   }
   
   @Override
   public int increaseCount(int bno) {
        int result = dao.increaseCount(sqlSession,bno);
        
        return result; 
   }
   
   @Override
   public Board boardDetail(int bno) {
       Board board = dao.boardDetail(sqlSession,bno);
       
       return result; 
   }
   
   @Override
   public int boardInsert(Board b) {
      int result = dao.boardInsert(sqlSession,b);
      
      return result;
   }   
   
   @Override
   public int updateBoard(Board b) {
      int result = dao.updateBoard(sqlSession,b);
      
      return result;
   }   
   

}
*/
