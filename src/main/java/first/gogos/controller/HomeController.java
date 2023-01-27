package first.gogos.controller;
import first.gogos.domain.Board;
import first.gogos.domain.Member;
import first.gogos.service.BoardService;
import first.gogos.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;


/**
 * 각 페이지 test하기 좋게 HomeController에 test데이터 하나 만들게 코드 작성해둠.
 * 여러개 작성해두면 현재 email에 unique 제약조건을 못걸어서 단건조회 쿼리에서 예외발생.
 */

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private final MemberService memberService;
    static boolean check = false;
    private static Member member;
    @GetMapping("/")
    public String home(Model model) {
        if (!check) {
            member = new Member();
            check = true;
            member.setJoindate();
            member.setEmail("1234");
            member.setNickname("gogos");
            member.setPassword("1234");
            memberService.join(member);
        }
        for(int i=1; i<=50; i++){
            Board board = new Board();
            board.setMember(member);
            board.setTitle(i + "번째 게시물");
            board.setWriteTime(LocalDateTime.now());
            boardService.addPosting(board);
        }



        return "home";
    }
}
