package com.kh.spring.common.interceptor;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.vo.Board;

/*
 * 사용자가 버튼을 누르면 클라이언트 → 서버로 요청(request)을 보낸다.
 *
 * 이 요청은 먼저 프론트 컨트롤러(DispatcherServlet)에 도착하고,
 * DispatcherServlet은 HandlerMapping을 통해 같은 URL 패턴을 가진
 * 핸들러(컨트롤러 메서드)가 있는지 조회한다.
 *
 * 매핑되는 핸들러가 있으면 DispatcherServlet은 이를 HandlerAdapter에 전달하고,
 * HandlerAdapter가 실제 컨트롤러를 실행한다. 이때 컨트롤러가 논리적인 경로 이름을 반환한다.
 *
 * 이후 뷰 리졸버(ViewResolver)가 논리적인 경로 이름을 물리적인 경로로 변환한다.
 *
 * 뷰가 화면을 렌더링하기 직전까지 Model에는 일시적으로 화면에 필요한 데이터가 담긴다.
 *
 * 뷰가 실제로 렌더링되어 HTML이 만들어지면 서버가 클라이언트에게 응답(response)을 보낸 것이다.
 *
 * 요청이 끝났기 때문에 Model에 담겼던 데이터는 사라지며,
 * 사용자가 새로운 요청을 하면 이전 Model 데이터는 유지되지 않는다.
 */


/*
 * 요청을 가로채서 특정 권한을 체크하거나,로그를 기록하거나, 공통적으로 사용해야하는 코드를 처리한다.
 * 
 * 필터와의 차이점 - 간섭시점이 다르다.
 * 필터 : 디스패처 서블릿 전 시점(인코딩 작업)
 * 인터셉터 : 디스패처 서블릿 후 컨트롤러 전 (권한 및 추가 작업)
 * 
 * 인터셉터를 사용하기 위해 HandlerInterceptor를 구현하기 (컨트롤러로 매핑하기전에 잠깐 권한을 가로챔) 
 * 
 * 스프링 MVC는 DispatcherServlet을 프론트 컨트롤러로 사용하며, 모든 요청을 먼저 받아 HandlerMapping에 전달 한다.
 * HandlerMapping은 URL과 매핑되는 Controller 메서드를 찾아 DispatcherServlet에 넘겨주고,
 * DispatcherServlet은 그 Handler를 실행하여 요청을 처리한다. 
 */

/*
 * prehandle은 컨트롤러 로직 실행 전에 수행하는 메서드이니까 바인딩 객체가 없는거고 
 * (물론, HttpRequestServlet, HttpResponseServlet은 존재) 
 * 왜냐하면 로직을 실행하려면 파일에서 form태그든 location.href가 있어야 실행이 되서
 * 데이터가 넘어갈텐데 안넘어가면 바인딩 객체가 없으니까 posthandle은 이제 데이터가 다 넘어온거고 
 * 물론 뷰 리졸버로 가기 전에 posthandle 실행
 */
public class TestInterceptor implements HandlerInterceptor{
	
	//간섭 시점 1 : 요청 처리 전
	//요청은 이미 도착은 했지만 컨트롤러가 실행하기 직전의 상태 , request, response 객체는 주입 받음
	//그런데 바인딩 객체같은경우에는 파일에서 받아와야 하는데 보통 form태그에서 갖고오고 그때 action에 url이 있으니 그 url에 맞게 컨트롤러가 실행되므로.. 아무튼 파일에 있는 데이터가 넘어오기 전이니까
	//넘어오기 직전이므로 바인딩 객체는 존재하진 않음. 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //request : 사용자는 무엇을 요청했는가
    	//response : 사용자에게 보낼 정보가 있는가
    	//handler : 이 요청은 누가 처리할 것인가.
    	
//    	System.out.println("====테스트 인터셉터 요청 처리 전====");
//    	System.out.println(request.getSession().getAttribute("loginMember")); //로그인 정보확인
//    	System.out.println(request);
//        System.out.println(response);
//        System.out.println(handler); // 매핑 주소에 맞는 컨트롤러
        
        //반환값이 true여야 기존 흐름을 유지한다. false면 기존흐름 방지 처리
    	return HandlerInterceptor.super.preHandle(request, response, handler);
        //response.sendRedirect(request.getContextPath()); // 메인페이지로 보내기 (응답뷰 지정)
        //return false; 
    }
    
    //간섭 시점 2 : 요청 처리 후 (view가 만들어지기 전)
    /*
     * 컨트롤러에서 정상적으로 리턴 후, 
     * 뷰 렌더링(화면에 파일을 띄울때) 직전에 실행. 
     * 컨트롤러가 실행을 다하고나서 뷰로 넘어가기 직전 
     * 뷰 리졸버로 넘어가기 직전이라 논리적인 경로 이름을 반환 받고 물리적인 경로 이름은 반환받지 못했다고 생각하면된다. 
     * 
     * 로직을 실행하고 뷰 리졸버로 넘어가기 직전(즉 return "논리적인 뷰이름"으로 가기 전)이니까 바인딩 객체는 있음. 
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    		ModelAndView modelAndView) throws Exception {
    	
    	//request : 사용자는 무엇을 요청했는가
    	//response : 사용자에게 보낼 정보가 있는가
    	//handler : 이 요청은 누가 처리할것인가
    	//modelandview : model(전달데이터) / view(이동할 뷰 페이지 정보)
    	
//    	System.out.println("=====테스트 인터셉터 요청 처리 후 =====");
//    	System.out.println(request);
//    	System.out.println(response);
//    	System.out.println(handler);
//    	System.out.println(modelAndView);
    	
    	/*
    	 * modelAndView에 데이터가 안담아진 이유 ("/login.me") 기준
    	 * 
    	 * 리다이렉트를 하면 뷰리졸버로 가는게 아닌 설정된 경로로 다시 가는거기때문에
    	 * 
    	 * 그 경로에 맞는 컨트롤러로 간다. 그럼 model에 값을 담아줬다고 하더라도 값이 안담긴다.
    	 * 
    	 * 왜냐? 리다이렉트도 요청인데 새로운 요청이니까 값이 남을리가없다. (물론 session은 유지) 
    	 * 
    	 * 그리고 멤버컨트롤러에 urlPattern이 login.me인 컨트롤러를 봐도
    	 * 
    	 * 모델에 값 자체를 담진 않았음. 만약 담았으면 나왔을 것이다. 
    	 */
    	
    	//model에 담긴 값을 조작할 수 있지만 권장하지 않는다. 
//    	ArrayList<Board> list = new ArrayList<>(); // 빈 리스트
//        modelAndView.addObject("list", list); // 빈 리스트 담아주기
    	
    	//뷰 정보 조작하기 (조건에 따라서 다른 페이지 보여주기 작업 가능)
    	//modelAndView.setViewName("common/errorPage"); //에러페이지로 보내기 
    	
    	HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    
    //간섭시점 3 : 사용자에게 출력되기 전(뷰 렌더링 이후니 사실상 끝난것) 
    /*
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    		throws Exception {
   
    	//request : 사용자는 무엇을 요청했는가
    	//response : 사용자에게 보낼 정보가 있는가
    	//handler : 이 요청은 누가 처리할것인가
    	//exception : 처리과정중 예외가 발생하였는가 
    	
    	System.out.println(ex);
    	
    	HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    */ 
}
