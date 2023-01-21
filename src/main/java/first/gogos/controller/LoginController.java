package first.gogos.controller;

import first.gogos.domain.LoginMember;
import first.gogos.domain.Member;
import first.gogos.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    @GetMapping("/members/login")
    public String loginPage(@ModelAttribute("loginMember") LoginMember loginMember) {
    //...... ModelAttribute loginMember로 해야되는데 member로 해가지고 개고생했음.. 조인쪽에 중복이라 그런듯 하다..
        return "login";
    }

    @PostMapping("/members/login") //아나 이거 왜 RequestParam안먹냐!!!!!!!!!다시봐보자 ppt부터.. //이거 html form action 고쳐서 해결함.
    public String login(@Valid @ModelAttribute LoginMember member, BindingResult result
                    , @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if (result.hasErrors()) { //bean validation으로 낫엠프티 검증에 걸린경우.
            return "login";
        }

        List<Member> findMemberByEmail = memberRepository.findByEmail(member.getEmail());
        if (findMemberByEmail.size() == 0) {
            result.addError(new FieldError("member", "email", "일치하는 Email이 없습니다."));
            return "login"; //여기서 FieldError 해준게, 저위에 기본 validation에서 생성해준 필드에러를 받는 쪽이랑 연결된듯.
        }

        if (findMemberByEmail.get(0).getPassword().equals(member.getPassword())) {
            //지금 왜 세션이 미리 있을땐 괜찮은데 없을땐 오류가 나지? true로 해야되는건가 아닌데
            //왜 세션이 미리 없으면. 세션ID가 겟요청으로 redirect랑 같이 날라가는거임 ?? -->yml설정으로 해결.구글링
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", findMemberByEmail.get(0));

            return "redirect:" + redirectURL;
        } else {
            result.addError(new ObjectError("member", "비밀번호가 일치하지 않습니다."));
            return "login"; //비밀번호 불일치.
        }
    }

    @GetMapping("/members/logout")
    public String logout(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
