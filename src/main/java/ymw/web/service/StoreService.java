package ymw.web.service;

import java.util.List;

import ymw.web.dto.FoodOption;
import ymw.web.dto.Review;
import ymw.web.dto.Store;
import ymw.web.dto.StoreDetail;

public interface StoreService {
	List<Store> storeList(int category, int address);

	StoreDetail storeDetail(long id);


	// 해당 메뉴의 옵션 가져오기
	List<FoodOption> foodOption(int foodId);
	
	void reviewWrite(Review review);
	
	void reviewModify(Review review);
//	List<FoodOption> foodOption(int foodId);
	
	// 찜
	void likes(long storeId, String likes, long userId);
	
	StoreDetail storeDetail(long id, long userId);
}