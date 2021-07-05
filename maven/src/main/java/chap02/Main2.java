package chap02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {
	private static AnnotationConfigApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {
		ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		// new hong@gmail.com 홍길동 1234 1234
				// change hong@gmail.com 1234 5678
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("명령어를 입력해 주세요");
			String cmd = reader.readLine();
			if (cmd.equals("exit")) {
				System.out.println("종료합니다");
				break;
			} else if (cmd.startsWith("new")) {
				processNewCommand(cmd.split(" "));
			} else if (cmd.startsWith("change")) {
				processChangeCommand(cmd.split(" "));
			} else if (cmd.equals("list")) {
				
			} else if (cmd.equals("list")) {
				Map<String, Member> map = ctx.getBean("listSvc", MemberListService.class).selectList();
				for(String key : map.keySet()) {
					Member m = map.get(key);
					System.out.println(m.getEmail()+"\t"+m.getName()+"\t"+m.getPassword()+"\t"+m.getRegisterDateTime());
				}
			} else if (cmd.startsWith("info")) {
				if (cmd.split(" ").length != 2){
					return;
				}
				Member member = ((MemberInfoService)ctx.getBean("infoSvc")).selectOne(cmd.split(" ")[1]);
				if (member == null) {
					System.out.println("등록되지않은 이메일입니다");
				} else {
					System.out.println("id:"+member.getId()+"email:"+member.getEmail()+"name:"+member.getName()+"date:"+member.getRegisterDateTime());
				}
			}
		}

	}
	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			return;
		}
		MemberRegisterService regSvc = ctx.getBean("regSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("등록완료");
		} catch (DuplicateMemberException e) {
			System.out.println("이메일중복");
		}
	}
	
	private static void processChangeCommand(String[]arg) {
		if (arg.length != 4) {
			return;
		}
		ChangePasswordService pwdSvc = ctx.getBean("pwdSvc", ChangePasswordService.class);
		try {
			pwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("비밀변호 변경");
		} catch (MemberNotFoundException e) {
			System.out.println("회원이 존재하지 않습니다");
		} catch (WrongIdPasswordException e) {
			System.out.println("이메일과 비밀번호가 일치하지 않습니다");
		}
	}
	

}
