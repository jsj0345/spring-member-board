package com.kh.spring.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;


@Repository
public class BoardDao {

	
	//게시글 개수
	public int listCount(SqlSessionTemplate sqlSession) {
		
		return sqlSession.selectOne("boardMapper.listCount");
	}
	
	//게시글 목록
	public ArrayList<Board> boardList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		
		//offset/limit 추가해주기
		int limit = pi.getBoardLimit(); //몇개씩 보여줄것인지
		int offset = (pi.getCurrentPage()-1)*limit; //몇개를 건너뛰고 보여줄것인지
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		//selectList의 두번째 매개변수는 파라미터 위치이기 때문에 rowBounds는 세번째 위치에 넣어야한다
		//파라미터가 없다고해도 null을 채워서 자리를 맞춰 전달해야한다.
		return (ArrayList)sqlSession.selectList("boardMapper.boardList",null,rowBounds);
	}

	public int increaseCount(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.update("boardMapper.increaseCount",bno);
	}

	public Board boardDetail(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.selectOne("boardMapper.boardDetail",bno);
	}

	public int boardInsert(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.insert("boardMapper.boardInsert",b);
		
	}

	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.update("boardMapper.updateBoard",b);
	}

	public int deleteBoard(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.delete("boardMapper.deleteBoard",bno);
	}

	public ArrayList<Board> searchList(SqlSessionTemplate sqlSession, HashMap<String, String> map, PageInfo pi) {
		
		
		int limit = pi.getBoardLimit(); //몇개씩 보여줄것인지
		int offset = (pi.getCurrentPage()-1)*limit;//몇개를 건너뛸것인지
		
		//RowBounds 객체 생성하기 
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		
		//매개변수 자리 잘 맞춰서 전달하기 (매퍼구문,파라미터,rowbounds)
		return (ArrayList)sqlSession.selectList("boardMapper.searchList",map,rowBounds);
	}
	
	//검색 게시글 개수 
	public int searchListCount(SqlSessionTemplate sqlSession, HashMap<String, String> map) {
		
		return sqlSession.selectOne("boardMapper.searchListCount",map);
	}

}

/*
@Repository
public class BoardDao {

  public int listCount(SqlSessionTemplate sqlSession) {
     return sqlSession.selectOne("boardMapper.listCount");
  }
  
  public ArrayList<Board> boardList(SqlSessionTemplate sqlSession, PageInfo pi) {
     
     // offset / limit 추가하기
     int limit = pi.getBoardLimit(); //몇개씩 보여줄것인지
     int offset = (pi.getCurrentPage()-1)*limit; // 몇개를 건너뛰고 보여줄것인지
     
     RowBounds rowBounds = new RowBounds(offset,limit);
     
     //selectList의 두번째 매개변수는 파라미터 위치이기 때문에 rowBounds는 세번째 위치에 넣어야한다. 
     //파라미터가 없다고 해도 null을 채워서 자리를 맞춰 전달해야한다.
     return (ArrayList) sqlSession.selectList("boardMapper.boardList",null,rowBounds); 
  }
  
  public int increaseCount(SqlSessionTemplate sqlSession, int bno) {
     return sqlSession.update("boardMapper.increaseCount", bno);
  }
  
  public Board boardDetail(SqlSessionTemplate sqlSession, int bno) {
     return sqlSession.selectOne("boardMapper.boardDetail", bno);
  }
  
  public int boardInsert(SqlSessionTemplate sqlSession, Board b) {
     return sqlSession.insert("boardMapper.boardInsert", b); 
  }
     
  
    

}
*/