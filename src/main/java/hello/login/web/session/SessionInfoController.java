package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null){
            return "세션X";
        }

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        //세션 생명 시간
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        //세션 만든 시간
        log.info("creationTime={}", new Date(session.getCreationTime()));
        //세션에 마지막으로 접근한 시간
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        //세션을 지금 당장 만든 거라면 true
        log.info("isNew={}", session.isNew());

        return "세션 출력";
    }
}
