package first.gogos.repository;

import first.gogos.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void 회원_단건_조회(){
        em.createQuery("select m from Member m where m.email =: email", Member.class)
                .setParameter("email", "worms0905@gmail.com")
                .getSingleResult();



    }

}