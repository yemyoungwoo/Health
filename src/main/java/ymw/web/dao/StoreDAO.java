package ymw.web.dao;

import java.util.List;
import java.util.Map;

import ymw.web.dto.Store;

public interface StoreDAO {
	 
	List<Store> storeList(Map<String, Object> map);
 
}
