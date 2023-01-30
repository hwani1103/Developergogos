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

        setPaging(pageNav, paging, N, page);

        if (pageNav == 0) pageNav = 1;
        List<Board> list = boardService.getList(pageNav);
        model.addAttribute("boardList", list);
        model.addAttribute("paging", paging.getNav());
        model.addAttribute("page", page);

        return "boardlist";
    }

    static int navSize;
    private static void setPaging(int pageNav, Paging paging, int N, StartEndPage page) throws Exception {
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
            if (x < pageNav && pageNav <= x + 10) {
                navSize = index % 10;
            } else navSize = 10;
        }

        System.out.println("최종 navSize = " + navSize);
        if(navSize==0) navSize=1;
        paging.setNav(new int[navSize]);

        int k = ((pageNav - 1) / 10) * 10;
        for (
                int i = 0;
                i < navSize; i++) {
            paging.nav[i] = i + k + 1;
        }

        page.setStartPage(paging.nav[0] - 1);
        System.out.println("getStartPage = " + page.getStartPage());
        if (paging.nav[navSize - 1] * 20 == N) {
            page.setEndPage(-1);
        } else
            page.setEndPage(paging.nav[navSize - 1] + 1); // 마지막원소가 10? 일땐 200개. 20?일땐 400개잖아 . 여기서 +1하기전까지가 마지막원소인 10이고
        // 이거 셋팅하기전에 조건 하나 걸어야됨. 총 게시물수를 가져오는 로직을 일단 짜야됨. 그래서
        // 총 게시물수가, 10 * 20이랑 일치할 경우에만 endPage를 -1로 설정하자. 그러면 >가 안보일것

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
                          Model model) {
        board.setBoardCategory(BoardCategory.자유게시판);
        board.setWriteTime(LocalDateTime.now());
        board.setMember((Member) session.getAttribute("loginMember"));
        //새로 만들어서 온 게시물 Board 객체에. 로그인 당시 사용자 정보를 입력해서 DB에 넣는다. 이거까지는 맞는듯?
        //그다음. board정보를 DB에 add. 결과로 받은건 Id(PK). 그걸 다시 read 해서 posing에 뿌린다.
        //그럼 이 때의 아 이게 맞는데?
        Long aLong = boardService.addPosting(board);
        Board read = boardService.read(aLong);

        model.addAttribute("read", read);

        return "posting";  // 개별 게시물 설정한다음 PRG 패턴 적용해야됨.
    }

    @GetMapping("read/{postingNo}")
    public String read(@PathVariable Long postingNo, Model model, HttpSession session){
        Board read = boardService.read(postingNo);
        model.addAttribute("read", read);
        return "posting";

    }

    //posting 화면에서 수정하기를 누른다. 겟 요청으로 간다. 그 때 postingNo를 전달해줘야 한다.
    //어떻게 전달해주냐
    @GetMapping("update/{postingNo}")
    public String Update(@PathVariable Long postingNo, Model model){
        //여기서 update에 Member를 안넣어줬네
        Board update = boardService.read(postingNo); //수정하려는 대상 게시글의 id를 얻어온 후 조회쿼리를 날려서 게시물을 얻어온다.
        model.addAttribute("update", update); // 다시 얻어온 게시물을 model을 통해 수정 페이지로 띄움.
        return "posting_update";
    }

    /**
     * GetMapping을 통해 수정하기 화면으로 들어간다. 그때 Board객체를 전달해준다. update라는이름이더좋겠네
     * 아무튼. 그렇게 간거임. update객체에는 그 id에 맞는 타이틀과 컨텐츠가 저장되어있었던게 보인거고
     * 그걸 post로 받으면서 변경된내용을 함 뿌려보자
     */
    @PostMapping("update/{postingNo}") // 변경사항을 받은다음에. 그 아 id로 보드 얻어오면되지일단.
    public String update(@PathVariable Long postingNo, Model model, Board update, HttpSession session){
        // Update쿼리 작성해서 update객체에 담긴 정보로 처리하면됨.
        // update board set title = update.title content = update.content where id = update.id 이런식인데. JPQL로 어찌하여 안될까요
        update.setId(postingNo);
        Board board1 = boardService.read(postingNo);
        Board updateResult = boardService.update(update);
//        updateResult.setMember((Member) session.getAttribute("loginMember")); // 이게 아니라
        updateResult.setMember(board1.getMember());
        model.addAttribute("read", updateResult);

        return "posting";
    }

    @GetMapping("delete/{postingNo}")
    public String delete(@PathVariable Long postingNo){
        boardService.delete(postingNo);
        return "redirect:/";
    }
}


/**
 * read. 저기로 갈 요청을 먼저 만들어야지. 음. 링크.a링크.보드리스트.ok
 */
/**
 *  @GetMapping("/list/{pageNav}")
 *     public String boardList(@PathVariable int pageNav, Model model) throws Exception {
 *         Paging paging = new Paging();
 *         StartEndPage page = new StartEndPage();
 */




