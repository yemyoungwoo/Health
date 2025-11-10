package ymw.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ymw.web.login.LoginDetailService;
import ymw.web.login.LoginFail;
import ymw.web.login.LoginSuccess;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
    @Autowired
    private LoginFail loginFail;

    @Autowired
    private LoginSuccess loginSuccess;

    @Autowired
    private LoginDetailService loginDetailService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccess)
                .failureHandler(loginFail)
            .and()
                .logout()
                .logoutSuccessUrl("/mypage")
            .and()
                .rememberMe()
                .key("rememberKey")
                .rememberMeCookieName("rememberMeCookieName")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .userDetailsService(loginDetailService);
    }
}
