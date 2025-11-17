package ymw.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ymw.web.dto.Join;
import ymw.web.dto.Review;
import ymw.web.dto.User;
import ymw.web.login.LoginService;
import ymw.web.service.StoreService;
import ymw.web.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private SqlSession sql;

	@GetMapping("/myPage")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("/user/myReview")
	public String myReview(@AuthenticationPrincipal LoginService user, Model model) {
		if (user == null) {
			return "redirect:/login";
		}
		
		long userId = user.getUser().getId();
		List<Review> reviewList = storeService.userReviewList(userId);
		model.addAttribute("reviewList", reviewList);
		
		return "user/myReview";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpSession session) {
		String referer = (String) request.getHeader("referer");
		session.setAttribute("referer", referer);
		return "user/login";
	}

	@GetMapping("/join")
	public String join() {
		return "user/join";
	}

	// form 태그의 메소드 타입이 post타입이라,, 여기서도 post씀
	@PostMapping("/join")
	public String joinProc(@Valid Join join, BindingResult bindingResult, Model model) {
		
		// validation 체크를 먼저 수행
		if (bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
			Map<String, String> errorMsg = new HashMap<>();
			for (int i = 0; i < list.size(); i++) {
				String field = list.get(i).getField();
				String message = list.get(i).getDefaultMessage();
				errorMsg.put(field, message);
			}
			model.addAttribute("errorMsg", errorMsg);
			return "user/join";
		}

		// validation 통과 후 비밀번호 인코딩 및 회원가입
		String encPwd = pwdEncoder.encode(join.getPassword());
		join.setPassword(encPwd);
		userService.join(join);

		return "redirect:/login";
	}

	@ResponseBody
	@GetMapping("/overlapCheck")
	public int overlapCheck(String value, String valueType) {
//		value = 중복체크할 값
//		valeuType = username, nickname
		System.out.println(value);
		System.out.println(valueType);
		int count = userService.overlapCheck(value, valueType);

		System.out.println(count);
		return count;
	}
	
	// 아이디 찾기 페이지
	@GetMapping("/findUsername")
	public String findUsernamePage() {
		return "user/findUsername";
	}
	
	// 아이디 찾기 처리
	@ResponseBody
	@PostMapping("/findUsername")
	public ResponseEntity<String> findUsername(String email) {
		try {
			userService.findUsername(email);
			return new ResponseEntity<>("아이디가 이메일로 전송되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// 비밀번호 찾기 페이지
	@GetMapping("/findPassword")
	public String findPasswordPage() {
		return "user/findPassword";
	}
	
	// 비밀번호 찾기 처리
	@ResponseBody
	@PostMapping("/findPassword")
	public ResponseEntity<String> findPassword(String username, String email) {
		try {
			userService.findPassword(username, email);
			return new ResponseEntity<>("임시 비밀번호가 이메일로 전송되었습니다.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	// 임시: 기존 사용자 비밀번호를 BCrypt로 변환하는 유틸리티
	// 사용법: /convertPassword?username=root&plainPassword=123
	// 주의: 프로덕션에서는 이 엔드포인트를 제거하거나 보안을 강화해야 합니다!
	@ResponseBody
	@GetMapping("/convertPassword")
	public String convertPassword(String username, String plainPassword) {
		try {
			// 사용자 조회
			User user = sql.selectOne("user.login", username);
			
			if (user == null) {
				return "사용자를 찾을 수 없습니다: " + username;
			}
			
			// 이미 BCrypt 형식인지 확인
			if (user.getPassword() != null && user.getPassword().startsWith("$2a$")) {
				return "이미 BCrypt 형식입니다: " + username;
			}
			
			// plainPassword가 제공되지 않으면 기존 비밀번호 사용
			String passwordToEncode = (plainPassword != null && !plainPassword.isEmpty()) 
				? plainPassword 
				: user.getPassword();
			
			// BCrypt로 인코딩
			String encodedPassword = pwdEncoder.encode(passwordToEncode);
			
			// 데이터베이스 업데이트
			Map<String, String> params = new HashMap<>();
			params.put("username", username);
			params.put("password", encodedPassword);
			sql.update("user.updatePassword", params);
			
			return "비밀번호 변환 완료: " + username + " (BCrypt로 변환됨)";
		} catch (Exception e) {
			return "오류 발생: " + e.getMessage();
		}
	}
	@GetMapping("/user/myInfo")
	public String myInfo() {
		return "user/myInfo";
	}

}
