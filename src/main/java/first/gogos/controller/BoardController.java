package first.gogos.controller;

import first.gogos.domain.*;
import first.gogos.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list/{pageNav}")
    public String boardList(@PathVariable int pageNav, Model model) throws Exception {
        Paging paging = new Paging();
        StartEndPage page = new StartEndPage();
        int N = boardService.getCount();
        System.out.println("총 게시물 수  = " + N);

        extracted(pageNav, paging, N, page);

        if (pageNav == 0) pageNav = 1;
        List<Board> list = boardService.getList(pageNav);
        model.addAttribute("boardList", list);
        model.addAttribute("paging", paging.getNav());
        model.addAttribute("page", page);

        return "boardlist";
    }


    /**
     * i = 200 i / 20 = 10
     * i = 201 i / 20 = 11
     */
    static int navSize;

    private static void extracted(int pageNav, Paging paging, int N, StartEndPage page) throws Exception {

        System.out.println("기존 pageNav = " + pageNav);         // pageNav : 몇 번째 페이지의 게시물들을 볼건지를 뜻하는 Navgiation 숫자.
        int index = (int) Math.ceil(N / 20.0f);                 // index : 게시물 수 나누기 20을 반올림함. 네비 사이즈를 결정하기 위한 변수.
        System.out.println("index = " + index);                 // 게시물이 200개까지는 index가 10이고, 201개부터는 11이다. 즉 게시물 200개까지는 navSize가 10이면 되고
        if (pageNav > index) pageNav = index;                   // 게시물이 201개인데 pageNav가 11일 경우 navSize는 1이어야 한다.
        System.out.println("pageNav = " + pageNav);

        int x = index / 10 * 10;
        if (index < 10) {
            navSize = index % 10;
        } else if (index == 10) {
            navSize = 10;
        } else if (index > 10) {
            if (x < pageNav && pageNav <= x+10) {
                navSize = index % 10;
            } else navSize = 10;
        }

        System.out.println("최종 navSize = " + navSize);
        paging.setNav(new int[navSize]);

        int k = ((pageNav - 1) / 10) * 10;
        for (
                int i = 0;
                i < navSize; i++) {
            paging.nav[i] = i + k + 1;
        }

        page.setStartPage(paging.nav[0] - 1);
        System.out.println("getStartPage = " + page.getStartPage());
        page.setEndPage(paging.nav[navSize - 1] + 1);
        System.out.println("getEndPage = " + page.getEndPage());
        System.out.println("navSize = " + navSize);
        System.out.println("paging.nav.length = " + paging.nav.length);
        System.out.println("pageNav = " + pageNav);
        System.out.println("N = " + N);
    }

    @GetMapping("/write")
    public String posting(@ModelAttribute("board") Board board
            , @ModelAttribute("comment") Comment comment) {
        return "boardwrite";
    }

    @PostMapping("/write")
    public String posting(@ModelAttribute("board") Board board, HttpSession session,
                          @ModelAttribute("comment") Comment comment) {
        board.setBoardCategory(BoardCategory.자유게시판);
        board.setWriteTime(LocalDateTime.now());
        board.setMember((Member) session.getAttribute("loginMember"));
        boardService.addPosting(board);
        return "posting";
    }
}




