package ymw.web.dao;

import java.util.List;
import java.util.Map;

import ymw.web.dto.Food;
import ymw.web.dto.FoodOption;
import ymw.web.dto.Store;

public interface StoreDAO {
	 
	List<Store> storeList(Map<String, Object> map);

	Store storeDetail(long storeId);
	
	List<Food> foodList(long storeId);
 
	List<FoodOption> foodOption(int foodId);
	
}
