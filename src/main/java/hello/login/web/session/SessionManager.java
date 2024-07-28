package hello.login.web.session;


/*
세션 관리
 */

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionID";
    //ConcurrentHashMap : 여러 요청이 동시에 들어올 시, 사용하는 자료구조
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /*
    세션 생성
    1. sessionId 생성(랜덤 값)
    2. 세션 저장소에 sessionId와 보관할 값 저장
    3. sessionId로 응답 쿠키 생성해서 클라이언트에게 전달
     */


    public void createSession(Object value, HttpServletResponse response) {
        //세션 id를 생성 후, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);

    }

    //세션 조회
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if(sessionCookie == null){
            return null;
        }

        return sessionStore.get(sessionCookie.getValue());
    }

    //세션 만료
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    public Cookie findCookie(HttpServletRequest request, String cookieName){
        if(request.getCookies() == null){
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                //findAny() : 제일 빨리 찾은 대상 1개 반환
                .findAny()
                .orElse(null);
    }

}
