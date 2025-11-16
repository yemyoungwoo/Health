package ymw.web.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImp implements AdminDAO {

	@Autowired
	private SqlSession sql;
	
	@Override
	public int pointUpdate(long userId, String info, int point) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("info", info);
		map.put("point", point);
		
		return sql.insert("admin.pointUpdate", map); 
	}
}