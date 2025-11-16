package ymw.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ymw.web.dto.Food;
import ymw.web.dto.FoodOption;
import ymw.web.dto.Review;
import ymw.web.dto.Store;

@Repository
public class StoreDAOImp implements StoreDAO {
 
	@Autowired
	private SqlSession sql;
	
	@Override
	public List<Store> storeList(Map<String, Object> map) {
		return sql.selectList("store.storeList", map);
	}
	
	@Override
	public Store storeDetail(long storeId) {
		return sql.selectOne("store.storeDetail", storeId);
	}
	
	@Override
	public List<Food> foodList(long id) {
		return sql.selectList("store.foodList", id);
	}
	
	@Override
	public List<FoodOption> foodOption(int foodId) {
		return sql.selectList("store.foodOption", foodId);
	}
	
	@Override
	public void reviewWrite(Review review) {
		sql.insert("store.reviewWrite", review);
	}
	
	@Override
	public List<Review> reviewList(long id) {
		return sql.selectList("store.reviewList", id);
	}
	
	@Override
	public void reviewModify(Review review) {
		sql.update("store.reviewModify", review);
	}
}