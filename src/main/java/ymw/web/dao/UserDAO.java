package ymw.web.dao;

import ymw.web.dto.Join;

public interface UserDAO {
	void join(Join join);
	int overlapCheck(String value, String valueType);
}