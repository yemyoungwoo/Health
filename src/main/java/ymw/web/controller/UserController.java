package ymw.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/myPage")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
}
