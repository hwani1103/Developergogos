package first.gogos;

import first.gogos.domain.Member;
import first.gogos.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Member member = new Member();
        member.setEmail("rlaworms0905@naver.com");
        member.setNickname("gogos");
        member.setPassword("qkdrnqkdrn");
        member.setJoindate();


        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

        Assertions.assertThat(findMember.getNickname()).isEqualTo(member.getNickname())
        ;
        Assertions.assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성

    }
}