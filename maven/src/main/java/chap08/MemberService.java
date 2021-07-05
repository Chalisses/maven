package chap08;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface MemberService {

	List<MemberVo>selectList();

	MemberVo login(MemberVo vo);
	
	MemberVo mypage(int mno);

	String mypage(HttpSession sess, Model model);

	void update(MemberVo vo);
	
}
