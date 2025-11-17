package ymw.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ymw.web.login.LoginService;
import ymw.web.service.UserService;
import ymw.web.util.UserInfoSessionUpdate;

@Controller
public class UserInfoController {

	@Autowired
	private BCryptPasswordEncoder encodePwd;
	
	@Autowired
	private UserService userService;
	

	// 내 비밀번호, 닉네임 수정하기
	@PatchMapping("/user/info")
	public ResponseEntity<String> modifyInfo(String value, String valueType, String prevPassword, 
	        @AuthenticationPrincipal LoginService user, HttpSession session) {
	    // value = 변경할 값
	    // valueType = password, nickname, phone
	    String username = user.getUser().getUsername();
	    String msg = "";
	    
	    switch (valueType) {
	    case "password":
	        if(!encodePwd.matches(prevPassword, user.getPassword())) {
	            return new ResponseEntity<String>("현재 비밀번호가 일치하지 않습니다", HttpStatus.OK);
	        } 
	        value = encodePwd.encode(value);
	        msg = "비밀번호가 변경되었습니다";
	        break;
	        
	    case "nickname":
	        msg = "닉네임이 변경되었습니다";
	        break;
	    case "phone":
	        msg = "전화번호가 변경되었습니다";
	        session.setMaxInactiveInterval(0);
	        session.setAttribute("authNum", null);
	        break;
	    }
	    
	    userService.modifyInfo(username, valueType, value);
	    UserInfoSessionUpdate.sessionUpdate(value, valueType, user);
	    
	    return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	// 인증번호 보내기
	@PostMapping("/send/authNum")
	private ResponseEntity<String> authNum(String phone, String email, HttpSession session){
	    String authNum = "";
	    for(int i=0;i<6;i++) {
	        authNum += (int)(Math.random() * 10);
	    }
	    
	    System.out.println("인증번호 : " + authNum);
	    
	    // 전화번호로 인증번호 보내기 추가
	    if(phone != null) {
	        System.out.println("전화번호로 인증번호 보내기");

	        
	        
	    // 이메일로 인증번호 보내기
	    } else if(email != null) {
	        System.out.println("이메일로 인증번호 보내기");
	        
	    }
	    
	    
	    Map<String, Object> authNumMap = new HashMap<>();
	    long createTime = System.currentTimeMillis(); // 인증번호 생성시간
	    long endTime = createTime + (300 *1000);	// 인증번호 만료시간
	    
	    authNumMap.put("createTime", createTime);
	    authNumMap.put("endTime", endTime);
	    authNumMap.put("authNum", authNum);
	    
	    session.setMaxInactiveInterval(300);
	    session.setAttribute("authNum", authNumMap);
	    
	    return new ResponseEntity<String>("인증번호가 전송되었습니다", HttpStatus.OK);
	}
	
	// 인증번호가 맞는지 확인
	@PostMapping("/send/authNumCheck")
	private ResponseEntity<String> authNumCheck(String authNum, HttpSession session){
	    Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute("authNum");
	    
	    String msg = "";
	    
	    if(sessionAuthNumMap == null) {
	        msg = "인증번호를 전송해주세요";
	        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
	    }
	    
	    // 인증번호 만료시간
	    long endTime = (long) sessionAuthNumMap.get("endTime");
	    
	    // 현재시간이 만료시간이 지났다면
	    if(System.currentTimeMillis() > endTime) {
	        msg = "인증시간이 만료되었습니다";
	        session.setAttribute(authNum, null);
	        session.setMaxInactiveInterval(0);
	        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
	    }
	    
	    // 인증번호
	    String sessionAuthNum = (String) sessionAuthNumMap.get("authNum");
	    
	    if(!authNum.equals(sessionAuthNum)) {
	        msg = "인증번호가 일치하지 않습니다";
	        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
	    } else {
	        // 인증번호가 일치하면
	        return new ResponseEntity<String>(HttpStatus.OK);
	    }
	}
}
