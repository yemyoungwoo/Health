package ymw.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ymw.web.dto.FoodOption;
import ymw.web.dto.Page;
import ymw.web.dto.Review;
import ymw.web.dto.Store;
import ymw.web.dto.StoreDetail;
import ymw.web.login.LoginService;
import ymw.web.service.StoreService;
import ymw.web.util.CookieManager;
import ymw.web.util.UploadFile;

@Controller
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private UploadFile uploadFile;

	@GetMapping("/store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {

		List<Store> storeList = storeService.storeList(category, address1 / 100);
		model.addAttribute("storeList", storeList);
		return "store/store";
	}

	@GetMapping("/store/detail/{id}")
	public String storeDetail(@PathVariable long id, Model model, @AuthenticationPrincipal LoginService user) {
		long userId = 0;
		if (user != null) {
			userId = user.getUser().getId();
		}

		StoreDetail storeDetail = storeService.storeDetail(id, userId);
		model.addAttribute("store", storeDetail);
		return "store/detail";
	}

	// 메뉴 클릭시 음식 추가옵션 가져요기
	@ResponseBody
	@GetMapping("/foodOption")
	public List<FoodOption> menuDetail(int foodId) {
		List<FoodOption> foodOption = storeService.foodOption(foodId);
		return foodOption;
	}

	// 리뷰 작성
	@PostMapping("/store/review")
	public String review(Review review, MultipartFile file, @AuthenticationPrincipal LoginService user)
			throws IOException {
		if (file.isEmpty()) {
			String img = "";
			review.setReviewImg(img);
		} else {
			String img = uploadFile.fildUpload(file);
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		storeService.reviewWrite(review);

		return "redirect:/orderList";
	}

	// 리뷰 수정
	@PostMapping("/store/reviewModify")
	public String reviewModify(Review review, MultipartFile file, @AuthenticationPrincipal LoginService user)
			throws IOException {
		if (!file.isEmpty()) {
			String img = uploadFile.fildUpload(file);
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		storeService.reviewModify(review);

		return "redirect:/orderList";
	}

	@ResponseBody
	@PostMapping("/store/reviewDelete")
	public ResponseEntity<Void> reviewDelete(long orderNum, @AuthenticationPrincipal LoginService user) {
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		int result = storeService.reviewDelete(orderNum, user.getUser().getId());

		if (result == 0) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 찜하기
	@ResponseBody
	@PostMapping("/store/likes")
	public ResponseEntity<Long> likes(long id, String likes, @AuthenticationPrincipal LoginService user) {
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		long userId = user.getUser().getId();
		storeService.likes(id, likes, userId);
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	// 찜한 가게 목록
	@GetMapping("/store/likes")
	public String likes(Model model, @AuthenticationPrincipal LoginService user) {
		long userId = 0;
		List<Store> likesList = new ArrayList<>();
		if (user == null) {

		} else {
			userId = user.getUser().getId();
			likesList = storeService.likesList(userId);
		}
		System.out.println("찜한 가게 : ");
		for (int i = 0; i < likesList.size(); i++) {
			System.out.println(likesList.get(i));
		}

		model.addAttribute("likesList", likesList);

		return "/store/likes";
	}

	@GetMapping({"/store/search", "/store/search/{page}"})
	public String search(Integer address1, String keyword, @PathVariable(required = false) Integer page, Model model) throws Exception {

	    CookieManager cm = new CookieManager();
	    LinkedHashSet<String> keywordList = cm.getKeywordList();
	    model.addAttribute("keywordList", keywordList);
	    
	    if(keyword == null || keyword.trim().isEmpty()) {
	        return "store/search";
	    }
	    
	    model.addAttribute("keyword", keyword);
	    
	    if(address1 == null) {
	        return "store/search";
	    }
	    
	    keywordList = cm.saveKeyword(keyword);
	    model.addAttribute("keywordList", keywordList);
	    
	    Page p = new Page(page);
	    List<Store> storeList = storeService.storeSearch(keyword, address1 / 100, p);
	    
	    if(storeList.size() == 0) {
	        model.addAttribute("noSearch", true);
	    } else {
	        p.totalPage(storeList.get(0).getListCount());
	        model.addAttribute("page", p);
	        model.addAttribute("storeList", storeList);
	    }

	    return "store/search";
	}
	
	@ResponseBody
	@DeleteMapping("/store/keyword-all")
	public ResponseEntity<Void> keywordDeleteAll() throws Exception {
	    CookieManager cm = new CookieManager();
	    cm.deleteKeywordAll();
	    return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping("/store/keyword-one")
	public ResponseEntity<Void> keywordDelete(String keyword) throws Exception {
	    CookieManager cm = new CookieManager();
	    cm.deleteKeyword(keyword);
	    return new ResponseEntity<>(HttpStatus.OK);
	}
}
