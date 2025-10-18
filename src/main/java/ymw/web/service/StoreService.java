package ymw.web.service;

import java.util.List;

import ymw.web.dto.Store;

public interface StoreService {
	List<Store> storeList(int category, int address);
}