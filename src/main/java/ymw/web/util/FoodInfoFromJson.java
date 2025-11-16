package ymw.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import ymw.web.dto.Cart;

public class FoodInfoFromJson {

	public static List<Cart> foodInfoFromJson(String foodInfoJSON) {
		Map<String, Object>  map = new HashMap<>();
		String[] arr = foodInfoJSON.split("/");
		Gson gson = new Gson();
		
		List<Cart> cartList = new ArrayList<>();
		for(int i=0;i<arr.length;i++) {
			cartList.add(gson.fromJson(arr[i], Cart.class));
		}
		return cartList;
	}
}