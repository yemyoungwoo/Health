package ymw.web.service;

import java.util.List;

import ymw.web.dto.Join;

public interface UserService {
	void join(Join join);
 
	int overlapCheck(String value, String valueType);

	void modifyInfo(String username, String valueType, String value);
	
	// 아이디 찾기 (여러 개일 수 있음)
	List<String> findUsername(String email) throws Exception;
	
	// 비밀번호 찾기 (임시 비밀번호 발급)
	String findPassword(String username, String email) throws Exception;
}