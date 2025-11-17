package ymw.web.util;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Page {
	private int view = 10;	// 화면에 출력할 목록 수	
	private int firstList;	// 페이지 첫번째 목록
	private int lastList;	// 페이지 마지막 목록
	
	public Page() {
		this(1);
	}
	
	public Page(int movePage) {
		page(movePage, view);
	}
	
    
    public void page(int movePage, int view) {
		this.firstList = (view * movePage) - view + 1;
		this.lastList = movePage * view; 
	}
}