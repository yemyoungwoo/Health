package ymw.web.dao;

import java.util.Map;

import ymw.web.dto.Join;

public interface UserDAO {
		void join(Join join);
	
		int overlapCheck(String value, String valueType);
		
		void modifyInfo(Map<String, Object> map);
}

