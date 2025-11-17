package ymw.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ymw.web.dao.UserDAO;
import ymw.web.dto.Join;

@Service
public class UserServiceImp implements UserService {
 
	@Autowired
	private UserDAO userDAO;
	
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
 
}