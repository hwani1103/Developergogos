package first.gogos.repository;

import first.gogos.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * findMember 메서드에서, getSingleResult()쪽에 문제있음. email에 unique제약조건을 못걸었고
 * query결과가 null일경우 예외발생함. 예외처리 일단 급한대로 하긴 했는데 리팩토링 필요
 */

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public Member findMember(String email) {
        Member member;
        try {
            member = em.createQuery("select m from Member m where m.email=:email", Member.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (RuntimeException e) {
            return null;
        }
        return member;
    }

}