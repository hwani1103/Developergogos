package first.gogos.controller;


import first.gogos.domain.Member;
import first.gogos.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     *아..이게 음.. 회원가입 누르면 . 여기로 와서 빈객체를 가지고 join.html로 가게 돼있구나.
     * 그거의 역할은? 빈객체의 역할은? 밸리데이션. 회원가입해서 딱 눌렀을때 검증에 실패하면
     * 다시
     */
    @GetMapping("/members/new")
    public String joinMember(@ModelAttribute("member") Member member){
        return "join";
    }

    @PostMapping("/members/new")
    public String join(@Valid @ModelAttribute("member") Member member, BindingResult result){
        if(result.hasErrors()){
            return "join";
        }

        member.setJoindate();
        memberRepository.save(member);
        return "redirect:/";

    }
}
