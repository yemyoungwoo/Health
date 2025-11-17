package ymw.web.service;

import java.util.List;

import ymw.web.dto.FoodOption;
import ymw.web.dto.Page;
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
	//??
	int reviewDelete(long orderNum, long userId);
//	List<FoodOption> foodOption(int foodId);
	
	// 찜
	void likes(long storeId, String likes, long userId);
	
	StoreDetail storeDetail(long id, long userId);
	
	// 찜한 가게들
	List<Store> likesList(long userId);
	
	List<Store> storeSearch(String keyword, int address, Page p);
}