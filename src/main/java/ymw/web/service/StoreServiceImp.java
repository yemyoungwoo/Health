package ymw.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ymw.web.dao.StoreDAO;
import ymw.web.dto.Store;
import ymw.web.dto.StoreDetail;

@Service
public class StoreServiceImp implements StoreService {
 
	@Autowired
	private StoreDAO storeDAO;
	
	@Override
	public List<Store> storeList(int category, int address) {
		Map<String, Object> map = new HashMap<>();
		map.put("category", category);
		map.put("address1", address);
		
		return storeDAO.storeList(map);
	}

	@Override
	public StoreDetail storeDetail(long id) {
		// TODO Auto-generated method stub
		return null;
	}
 
}