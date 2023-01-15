package first.gogos.controller;


import first.gogos.domain.Member;
import first.gogos.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(Model model){ //이건그냥 test용 data 만드는거..
        Member member = new Member();
        member.setJoindate();
        member.setEmail("rlaworms0905@naver.com");
        member.setNickname("gogos");
        member.setPassword("dhfl0123");
        memberRepository.save(member);

        System.out.println("홈 호출 ok");
        return "home";
    }


}
