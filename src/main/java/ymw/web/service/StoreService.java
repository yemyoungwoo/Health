package ymw.web.service;

import java.util.List;

import ymw.web.dto.Store;
import ymw.web.dto.StoreDetail;

public interface StoreService {
	List<Store> storeList(int category, int address);

	StoreDetail storeDetail(long id);
}