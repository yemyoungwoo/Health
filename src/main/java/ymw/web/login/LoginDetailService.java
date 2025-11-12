package ymw.web.login;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ymw.web.dto.User;

@Service
public class LoginDetailService implements UserDetailsService {
 
	@Autowired
	private SqlSession sql;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = sql.selectOne("user.login", username);

		if (user != null) {
			System.out.println("사용자 찾음: " + username);
			System.out.println("저장된 비밀번호 형식: " + (user.getPassword() != null && user.getPassword().startsWith("$2a$") ? "BCrypt" : "일반 텍스트"));
			System.out.println("비밀번호 길이: " + (user.getPassword() != null ? user.getPassword().length() : 0));
			
			LoginService loginDetail = new LoginService();

			loginDetail.setUser(user);

			return loginDetail;
		} else {
			System.out.println("사용자를 찾을 수 없음: " + username);
			throw new UsernameNotFoundException("유저없음");
		}
	}
}