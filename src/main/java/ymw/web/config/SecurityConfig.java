package ymw.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ymw.web.login.LoginDetailService;
import ymw.web.login.LoginFail;
import ymw.web.login.LoginSuccess;

@EnableWebSecurity
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginFail loginFail;

	@Autowired
	private LoginSuccess loginSuccess;
	
	@Autowired
	private LoginDetailService loginDetailService;
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginDetailService).passwordEncoder(encodePwd());
	}
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/**").hasAnyRole("ADMIN, USER")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/") // 인증 필요한 페이지 접근시 이동페이지
			.loginProcessingUrl("/login")
			.successHandler(loginSuccess)
			.failureHandler(loginFail)
		.and()
			.logout()
			.logoutSuccessUrl("/myPage")
			.invalidateHttpSession(true) //세션 삭제
			.deleteCookies("remember-me", "JSESSIONID") //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제
		.and()
			.rememberMe()
			.key("rememberKey")
			.rememberMeCookieName("rememberMeCookieName")
			.rememberMeParameter("remember-me")
			.tokenValiditySeconds(60 * 60 * 24 * 7)
			.userDetailsService(loginDetailService)
		;
//			/* 로그아웃 설정 */
//			.and()
//			  .logout()
//			  .logoutSuccessUrl("/")
//			  .invalidateHttpSession(true) //세션 삭제
//			  .deleteCookies("remember-me", "JSESSIONID"); //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제
		
	}
}