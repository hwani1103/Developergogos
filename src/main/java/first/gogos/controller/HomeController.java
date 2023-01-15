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
        member.setEmail("worms0905@gmail.com");
        member.setNickname("gogos");
        member.setPassword("1234");
        memberRepository.save(member);

        return "home";
    }


}
