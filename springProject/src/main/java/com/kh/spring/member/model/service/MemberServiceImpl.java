package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//Service는 인터페이스와 구현체(클래스)로 나누어 작성하여 의존성을 낮춘다.
//Service 인터페이스에서는 어떤 작업을 할것인지를 정해놓고 구현체에서 구현하여 사용하는 형식 
//Controller에서는 인터페이스에 있는 메소드를 호출하기 때문에 구현체가 달라지는것에 대한 영향을 받지 않을 수 있다. 


@Service // 서비스 어노테이션 부여
public class MemberServiceImpl implements MemberService {

	//DAO 호출하기 위해서 스프링에게 등록처리하기
	@Autowired
	private MemberDao dao;
	
	//SqlSession도 필요하니 스프링에게 등록처리하고 사용하기
	@Autowired
	private SqlSessionTemplate sqlSession; // root-context.xml에서 확인! 
	//스프링 컨테이너가 sqlSession의 생명주기도 관리하기 때문에 직접 close할 필요없고
	//트랜잭션 처리 또한 스프링이 처리하기 때문에 생략한다.
	//단 트랜잭션 처리를 직접 묶어서 해야하는 경우엔 메소드에
	//@Transactional 어노테이션을 부여한다. 
	
	@Override
	public Member loginMember(Member m) {
		
		Member loginMember = dao.loginMember(sqlSession, m); 
		
		return loginMember;
		
		//return dao.loginMember(sqlSession,m); 
	}

	@Override
	public int insertMember(Member m) {
		
		int result = dao.insertMember(sqlSession, m);
		
		
		return result;
	}

	@Override
	public int deleteMember(Member m) {
		int result = dao.deleteMember(sqlSession, m);
		
		return result; 
	}
	
	/*
	 * @Override
	 * public int deleteMember(String userId) {
	 *   return dao.deleteMember(sqlSession,userId);
	 * }
	 * 
	 */
	
	@Override
	public int updateMember(Member m) { //m1은 태그에서 입력해온 멤버, m2는 기존 로그인 정보를 담은 멤버. 
		int result = dao.updateMember(sqlSession,m);
		
		return result; 
	}

}
