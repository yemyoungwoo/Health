package ymw.web.service;

import ymw.web.dto.Join;

public interface UserService {
	void join(Join join);
 
	int overlapCheck(String value, String valueType);

	void modifyInfo(String username, String valueType, String value);
}