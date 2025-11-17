package ymw.web.dao;

import java.util.List;
import java.util.Map;

import ymw.web.dto.Join;
import ymw.web.dto.User;

public interface UserDAO {
		void join(Join join);
	
		int overlapCheck(String value, String valueType);
		
		void modifyInfo(Map<String, Object> map);
		
		// 아이디 찾기 (이메일로) - 여러 개일 수 있음
		List<String> findUsernameByEmail(String email);
		
		// 비밀번호 찾기 (아이디와 이메일로)
		User findUserByUsernameAndEmail(String username, String email);
		
		// 임시 비밀번호로 업데이트
		void updateTempPassword(String username, String tempPassword);
}

