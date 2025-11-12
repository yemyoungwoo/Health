package ymw.web.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFail implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        String username = request.getParameter("username");
        System.out.println("로그인 실패: " + exception.getMessage());
        System.out.println("시도한 사용자명: " + username);
        System.out.println("예외 클래스: " + exception.getClass().getName());
        
        if (exception.getCause() != null) {
            System.out.println("원인: " + exception.getCause().getMessage());
        }
        
        response.sendRedirect("/login?error=true");
    }
}