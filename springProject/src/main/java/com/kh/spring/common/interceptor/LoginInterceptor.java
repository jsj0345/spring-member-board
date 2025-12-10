package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/*
 * 로그인 권한을 걸어두는 인터셉터 활용 잘해보기 
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	//1. 간섭 시점 1 : 요청 처리 전 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//request : 사용자는 무엇을 요청했는가
    	//response : 사용자에게 보낼 정보가 있는가
    	//handler : 이 요청은 누가 처리할 것인가.
		
		HttpSession session = request.getSession();
    			
		if(session.getAttribute("loginMember")!=null) { // 로그인 정보가 있으면 기존 흐름 유지 
			return true;
		} else { // 없으면 유지하는거 중지, 대신 메인페이로 갈수있게끔. 
			session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스 입니다.");
			response.sendRedirect(request.getContextPath());
			return false; 
		}
		
	}
	
	

}
