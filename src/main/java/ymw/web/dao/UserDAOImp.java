package ymw.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ymw.web.dto.Join;
import ymw.web.dto.User;

@Repository
public class UserDAOImp implements UserDAO {
 
	@Autowired
	private SqlSession sql;
	
	@Override
	public void join(Join join) {
		sql.insert("user.join" , join);	
	}
	
	@Override
	public int overlapCheck(String value, String valueType) {
		Map<String, String> map = new HashMap<>();
		map.put("value", value);
		map.put("valueType", valueType);
		
		return sql.selectOne("user.overlapCheck" ,map);
	}
	
	@Override
	public void modifyInfo(Map<String, Object> map) {
	    sql.update("user.modifyInfo", map);
	}
	
	@Override
	public List<String> findUsernameByEmail(String email) {
		return sql.selectList("user.findUsernameByEmail", email);
	}
	
	@Override
	public User findUserByUsernameAndEmail(String username, String email) {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("email", email);
		return sql.selectOne("user.findUserByUsernameAndEmail", map);
	}
	
	@Override
	public void updateTempPassword(String username, String tempPassword) {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("password", tempPassword);
		sql.update("user.updateTempPassword", map);
	}
 
}
