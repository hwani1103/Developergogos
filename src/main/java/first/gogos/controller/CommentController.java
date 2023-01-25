package first.gogos.controller;

import first.gogos.domain.Board;
import first.gogos.domain.BoardCategory;
import first.gogos.domain.Comment;
import first.gogos.domain.Member;
import first.gogos.repository.BoardRepository;
import first.gogos.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment/write")
    public String addComment(@ModelAttribute("comment") Comment comment, BindingResult result
    ,@ModelAttribute("board") Board board,@ModelAttribute("member") Member member){
        commentRepository.add(comment);//여기서 posting으로 보낼떄..아 ..board랑 멤버를 찾아와서 해야되네?
        board.setBoardCategory(BoardCategory.자유게시판);
        board.setId(100L);
        board.setTitle("그냥정함");
        board.setContent("그냥정했다리");
        board.setMember(member);
        board.getMember().setNickname("zz");


        return "posting";

    }

}
//댓글보다 페이징이 먼저인듯 하다~~~~~~~~
//이게 쉬운문제가 아니구만.
// 자기가 속한 게시판도 알아야하고 현재 로그인중인 ID도 알아야되구먼.
