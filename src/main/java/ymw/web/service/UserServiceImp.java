package ymw.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ymw.web.dao.UserDAO;
import ymw.web.dto.Join;
import ymw.web.dto.User;
import ymw.web.util.EmailService;

@Service
public class UserServiceImp implements UserService {
 
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void join(Join join) {
		userDAO.join(join);
	}
	
 
	@Override
	public int overlapCheck(String value, String valueType) {
		return userDAO.overlapCheck(value, valueType);
	}
	
	@Override
	public void modifyInfo(String username, String valueType, String value) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("username", username);
	    map.put("valueType", valueType);
	    map.put("value", value);
	    userDAO.modifyInfo(map);
	}
	
	@Override
	public List<String> findUsername(String email) throws Exception {
		List<String> usernameList = userDAO.findUsernameByEmail(email);
		
		if (usernameList == null || usernameList.isEmpty()) {
			throw new Exception("해당 이메일로 등록된 아이디가 없습니다.");
		}
		
		// 여러 개의 아이디를 HTML로 구성
		StringBuilder usernameHtml = new StringBuilder();
		for (String username : usernameList) {
			usernameHtml.append("<p style='font-size: 18px; font-weight: bold; color: #30DAD9; margin: 10px 0;'>")
			            .append(username)
			            .append("</p>");
		}
		
		// 이메일로 아이디 전송
		String subject = "[헬스 배달] 아이디 찾기";
		String content = "<h2>아이디 찾기 결과</h2>"
				+ "<p>요청하신 이메일로 등록된 아이디는 다음과 같습니다:</p>"
				+ usernameHtml.toString()
				+ "<p>감사합니다.</p>";
		
		emailService.sendEmail(email, subject, content);
		
		return usernameList;
	}
	
	@Override
	public String findPassword(String username, String email) throws Exception {
		User user = userDAO.findUserByUsernameAndEmail(username, email);
		
		if (user == null) {
			throw new Exception("아이디와 이메일이 일치하지 않습니다.");
		}
		
		// 임시 비밀번호 생성 (8자리)
		Random random = new Random();
		String tempPassword = "";
		for (int i = 0; i < 8; i++) {
			if (i < 4) {
				tempPassword += (char)('A' + random.nextInt(26)); // 대문자
			} else {
				tempPassword += random.nextInt(10); // 숫자
			}
		}
		
		// 비밀번호 암호화 후 업데이트
		String encodedPassword = passwordEncoder.encode(tempPassword);
		userDAO.updateTempPassword(username, encodedPassword);
		
		// 이메일로 임시 비밀번호 전송
		String subject = "[헬스 배달] 임시 비밀번호 발급";
		String content = "<h2>임시 비밀번호 발급</h2>"
				+ "<p>요청하신 임시 비밀번호는 다음과 같습니다:</p>"
				+ "<p style='font-size: 20px; font-weight: bold; color: #30DAD9;'>" + tempPassword + "</p>"
				+ "<p style='color: red;'>로그인 후 반드시 비밀번호를 변경해주세요.</p>"
				+ "<p>감사합니다.</p>";
		
		emailService.sendEmail(email, subject, content);
		
		return tempPassword;
	}
 
}