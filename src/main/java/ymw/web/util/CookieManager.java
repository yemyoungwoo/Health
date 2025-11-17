package ymw.web.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CookieManager {
	
	private static final String KEYWORD = "KEYWORD";
	private static final int KEYWORD_LIMIT = 5;

	//비회원 찜 ?
	public String findCookie(String cookieName) throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		Cookie[] cookies = attr.getRequest().getCookies();

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
			}
		}
		return null;
	}
	
	
	
		public void likes(long storeId) throws Exception  {
		final String LIKES_LIST = "LIKES_LIST";
		String cookie = findCookie(LIKES_LIST);
		List<Long> list = new ArrayList<>();
		
		if(cookie == null) {
			list.add(storeId);
			addCookie(LIKES_LIST, list.toString());
			System.out.println("찜 목록 = " + list);
			return;
		}
		
		StringTokenizer st = new StringTokenizer(cookie, ", ");
		
		while(st.hasMoreTokens()) {
			list.add(Long.parseLong(st.nextToken()));
		}
		
		if(list.contains(storeId)) {
			list.remove(storeId);
		} else {
			list.add(storeId);
		}
		
		if(list.size() == 0) {
			addCookie(LIKES_LIST, "");
		} else {
			addCookie(LIKES_LIST, list.toString());
		}
		
		System.out.println("찜 목록 = " + list);
	}
	
	
	public void addCookie(String name, String value) throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
		cookie.setMaxAge(60 * 60 * 24 * 30);
		cookie.setPath("/");
		attr.getResponse().addCookie(cookie);
	}
	//검색 저장할 메서드
	public LinkedHashSet<String> saveKeyword(String keyword) throws Exception {
	    LinkedHashSet<String> current = parseKeywordCookie(findCookie(KEYWORD));
	    LinkedHashSet<String> ordered = new LinkedHashSet<>();
	    ordered.add(keyword);
	    
	    for(String key : current) {
	        if(ordered.size() >= KEYWORD_LIMIT) {
	            break;
	        }
	        if(!key.equals(keyword)) {
	            ordered.add(key);
	        }
	    }
	    
	    updateKeywordCookie(ordered);
	    return ordered;
	}
	
	public LinkedHashSet<String> getKeywordList() throws Exception {
	    return parseKeywordCookie(findCookie(KEYWORD));
	}
	
	public void deleteKeywordAll() throws Exception {
	    addCookie(KEYWORD, "");
	}
	
	public LinkedHashSet<String> deleteKeyword(String keyword) throws Exception {
	    LinkedHashSet<String> list = parseKeywordCookie(findCookie(KEYWORD));
	    list.remove(keyword);
	    updateKeywordCookie(list);
	    return list;
	}
	
	private LinkedHashSet<String> parseKeywordCookie(String keywordList) {
	    LinkedHashSet<String> set = new LinkedHashSet<>();
	    
	    if(keywordList == null) {
	        return set;
	    }
	    
	    String clean = keywordList.replace("[", "").replace("]", "");
	    StringTokenizer st = new StringTokenizer(clean, ",");
	    
		while (st.hasMoreTokens() && set.size() < KEYWORD_LIMIT) {
			String key = st.nextToken().trim();
			if (!key.isEmpty()) {
				set.add(key);
			}
		}
	    
	    return set;
	}
	
	private void updateKeywordCookie(LinkedHashSet<String> set) throws Exception {
	    if(set.isEmpty()) {
	        addCookie(KEYWORD, "");
	    } else {
	        String value = set.stream().collect(Collectors.joining(","));
	        addCookie(KEYWORD, value);
	    }
	}
}