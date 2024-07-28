package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findById(String loginId){

        //stream() : 반복문 돌림
        //filter() : 조건 만족 시, 다음 단계
        //findFirst() : 반환되는 애가 있으면 그 대상을 반환하고 끝냄
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();

    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
