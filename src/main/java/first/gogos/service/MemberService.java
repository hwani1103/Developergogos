package first.gogos.service;

import first.gogos.domain.Member;
import first.gogos.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member){
        return memberRepository.save(member);
    }

    public Member findMemberByEmail(String email){
        return memberRepository.findMember(email);
    }
}
