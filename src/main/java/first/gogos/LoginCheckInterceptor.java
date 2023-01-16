package first.gogos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI(); // 접속 URI 얻어오기.
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false); // 세션 얻어오기.
        if (session == null || session.getAttribute("loginMember") == null) { //세션이 널이거나 로그인 세션 ID가 다르면
            log.info("미인증 사용자 요청"); // 미인증으로 간주하고
            //로그인으로 redirect
            response.sendRedirect("/members/login?redirectURL=" + requestURI); // 리다이렉트. URI 가지고감.
            return false;
        }
        return true;
    }
}