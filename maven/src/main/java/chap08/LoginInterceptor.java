package chap08;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	/*
	 preHandle : 컨트롤러 실행 전
	 postHandle : 컨츠롤러 싷행 후 (뷰 리턴전)
	 afterHandle :  뷰 실행 후 
	 */
	public boolean preHandler(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		// 세션객체에서 memberInfo를 가지고 와서 있으면(로그인되어 있으면) true
		// 없으면(미로그인) false
		HttpSession sess = req.getSession();
		MemberVo vo = (MemberVo)sess.getAttribute("memberInfo");
		if (vo == null) {
			res.setContentType("text/html;charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println("<script>");
			out.println("alert('로그인후 사용가능합니다.');");
			out.println("location.href='form.do';");
			out.println("</script>");
			return false;
		} else {
			return true;
		}
	}
}
