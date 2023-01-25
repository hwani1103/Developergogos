package first.gogos.controller;


import first.gogos.domain.LoginMember;
import first.gogos.domain.Member;
import first.gogos.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String join(@ModelAttribute Member member){
        return "join";
    }

    @PostMapping("/new")
    public String join(@Valid @ModelAttribute Member member, BindingResult result){
        if(result.hasErrors()){
            return "join";
        }
        member.setJoindate();
        memberService.join(member);
        return "joinsuccess";
    }

    //=====================로그인=========================//
    @GetMapping("/login")
    public String login(@ModelAttribute("loginMember") LoginMember loginMember) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginMember") LoginMember loginMember, BindingResult result,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        if (result.hasErrors()) {
            return "login";
        }
        Member findMemberByEmail = memberService.findMemberByEmail(loginMember.getEmail());
        if(findMemberByEmail==null){
            result.addError(new FieldError("member", "email", "일치하는 Email이 없습니다."));
            return "login";
        }
        if(findMemberByEmail.getPassword().equals(loginMember.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", findMemberByEmail);
            return "redirect:" + redirectURL;

        } else {
            result.addError(new ObjectError("member", "비밀번호가 일치하지 않습니다."));
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }





}
