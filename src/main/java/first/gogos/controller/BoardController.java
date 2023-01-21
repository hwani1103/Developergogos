package first.gogos.controller;

import first.gogos.domain.Board;
import first.gogos.domain.BoardCategory;
import first.gogos.domain.Comment;
import first.gogos.domain.Member;
import first.gogos.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping("/board/list")
    public String board(HttpSession session){
        return "boardlist";
    }

    @GetMapping("/board/write")
    public String boardwrite(@ModelAttribute("board") Board board
            , @ModelAttribute("comment") Comment comment){
        return "boardwrite";
    }


    @PostMapping("/board/write")
    public String boardWrite(@ModelAttribute("board") Board board, BindingResult result
    ,HttpSession session, @ModelAttribute("comment") Comment comment){
        board.setBoardCategory(BoardCategory.자유게시판);
        board.setWriteTime(LocalDateTime.now());
        board.setViewCnt(0);
        board.setMember((Member)session.getAttribute("loginMember"));
        boardRepository.add(board);
        return "posting"; //이거는 DB에 집어넣는거고. 객체에도 넣어주는게 연관관계 편의메서드.
    }



}
/**
 * //그냥 만들어보는거
 *         board.setBoardCategory(BoardCategory.자유게시판);
 *         board.setTitle("안녕하세요 가입인사드립니다.");
 *         board.setContent("가입 처음 했습니다. 웹사이트 저도 만들고 싶은데, 이정도 만드는데 얼마나 걸리셨나요? ");
 *         board.setWriteTime(LocalDateTime.now());
 *         board.setViewCnt(0);
 *         Member loginMember = (Member) session.getAttribute("loginMember");
 *         board.setMember(loginMember);
 *
 *
 *         boardRepository.add(board);
 */