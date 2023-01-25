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
    public String boardList(@PathVariable int pageNav, Model model) {
        Paging paging = new Paging(); // paging에는 배열이 있다..
        StartEndPage page = new StartEndPage(); // page는 그냥 <랑 >한테 어떤 값을 가리키게 할 건지를 정해주기 위함..
                                                // 더이상 보여줄 값이 없을때는 얘를 화면에 안나오게 해야될것같당
        int N = boardService.getCount();        // 실제 게시물의 총 수를 가져온다.
        System.out.println("총 게시물 수  = " + N);

        extracted(pageNav, paging, N, page);
        /**이 메서드가 하는 일
         * 1. pageNav를 받아서 한 게시물에 20개씩 보여주는 일을 한다.
         * 2. paging의 nav배열을 게시물 총 수를 바탕으로 1~10길이로 생성한다.
         * 3. 생성한 배열에 1~10까지 초기화함.
         * 4. page는 startPage와 endPage의 초기화.
         *
         */

        if (pageNav == 0) pageNav = 1;
        List<Board> list = boardService.getList(pageNav);
        model.addAttribute("boardList", list);
        model.addAttribute("paging", paging.getNav());
        model.addAttribute("page", page);

        return "boardlist";
    }

    static int navSize;
    private static void extracted(int pageNav, Paging paging, int N, StartEndPage page) {
        System.out.println("기존 pageNav = " + pageNav);
        int index = (int) Math.ceil(N / 20.0f);
        if(pageNav > index) pageNav = index; // pageNav는 총 게시물 수 나누기 20보다 클 수 없다.
        System.out.println("pageNav = " + pageNav);
        //============================================================================
        // 이건 지금 해결해야 할 문제는 게시물 200개일때 board/list/11이 들어오면 에러니까
        // 네비게이션을 다시 1~10으로 초기화하는건 위 코드로 해결됐지만
        // 실제 요청을 board/list/1이나 10이나, 제일좋은건 자기가 현재 보고있던 페이지를 그대로 다시 띄워주는걸 해야함.
        // 아니면 아예 <랑 >를 특정 조건하에서 안보여주게 해야겠네
        // 아 안보여주게 해야겠네.. 뭐하러 누르게 해놓고 안보이게함 못누르게해야지.
        //============================================================================


        // ex) pageNav = 11, paging, N = 200, page가 왔다.
        // 원래대로라면 pageNav=11인 page는 그냥 pageNav=1~10이랑 같은 네비게이션을 같고 게시물도 변화 없어야 함.
        // 1번 for문에서. navSize가 10으로 셋팅됨. 지금 navSize는 게시물 수에만 의존함. 게시물이 201개가 되는순간 navSize는 1이되는문제.
        // 2번 for문에서. int k = ((pageNav-1) / 10 ) * 10); 에서. pageNav가 11이니깐. pageNav가 10일땐 k가 0인데 11일땐 k가 10이 됨.
        // k가 10이 되어버리면.. paging.nav는 길이 10 배열인데, 초기화가 11부터 20까지로 돼버리겠네. ok

        // ex) pageNav = 11, paging, N = 201, page가 왔다.
        // 원래대로라면. 게시물이 1개 보이고 네비게이션도 11 하나가 보여야 함. 그러려면 배열의 길이가 1이 되어야 한다.
        // 일단 1번 for문에서. if(v%10!=0) 여기서. 200개까지는 false인데.0이니까. 201개부터는 true라서. (1이니까) navSize가 1이 되어버린다.
        // navSize가 1이 되어버리니까
        // 2번 for문에서. setNav의 배열길이가 1이 되어버리고
        // pageNav가 11이니까 k는 10인데
        // 배열길이는 1이니까 0번배열이 11이 됨. 이건 문제없네.
        /**
         * 결론은..
         * 총 게시물 수 + pageNav를 같이 비교한다음 navSize를 정해주어야 함.
         * 그리고 pageNav는. 총 게시물수 / 20까지만 보여줄 수 있으니까. 게시물이 200이면 pageNav는 10까지임. 근데 총 게시물 / 20보다 큰 pageNav가 들어오면. pageNav를 그냥 저값으로 수정하자.
         * 수정했음. 지금 게시물이 200개일때 pageNav 11이상 요청 들어오면 pageNav가 강제로 10으로 바뀌게됨. 대신에 요청이 board/list/11로 가기때문에
         * 네비게이션은 1~10을 보여주긴하는데 게시물은 안보임. 즉 board/list/10으로 가게끔 그거까지 변경해야됨
         * pageNav가 1~10인지, 11~20인지, 21~30인지, 31~40인지 +
         */
        for (int i = 0; i < N; i++) { //N은 총 게시물 수. 게시물 수 만큼 반복하면서
            int v = (int) Math.ceil(i / 20.0f); //20은 걍 pageSize. 게시물 / 20을 구한다. 즉 v는 navSize를 구하려는것. 게시물이 200개면 nav는 10개니까..
            if (v % 10 != 0 && pageNav > (N/20)) { // 게시물이 200개까지는 이 값이 0이어서 navSize는 else구문에서 10을 할당받는다.
                navSize = v % 10; // 하지만 게시물이 201개가 되는 순간. 나머지연산이 1이되므로 navSize가 1이 되어버림.
                                    // 저 if조건이 true일지라도 pageNav가 1~10이면 navSize는 10.
            } else                  // 이걸 똑똑하게 구하려면.. pageNav가 잠깐만.. pageNav가 10으로 내려올때도 있고, 11로 내려올때도 있음. 실제 게시물이 201개정도가 되어서
                navSize = 10;       // pageNav가 11로 내려온다면. if조건을 통과시키면 되고. 실제 게시물이 201개이지만 pageNav가 10으로내려온다면. 1~10을 보여줘야됨.
        }                           // 그 말은 N이 201개 이상일 때 pageNav가 11이상이면 true. N이 201개이상인데 pageNav가 10이하면 false.
                                    // N > 200 이면서. !! 해결했다.
                                    // pageNav가 전체 게시물 / 20보다 클때만 로직 적용하기로~~

        paging.setNav(new int[navSize]); //배열의 길이는 1부터 10까지가 나옴
        // 총 게시물 수를 바탕으로 필요한 nav배열을 생성해낸다.

        int k = ((pageNav - 1) / 10) * 10;  //
        for (int i = 0; i < navSize; i++) {
            paging.nav[i] = i + k + 1;
        }

        page.setStartPage(paging.nav[0] - 1); // 이러면 startpage는 1
        System.out.println("getStartPage = " + page.getStartPage());
        page.setEndPage(paging.nav[navSize - 1] + 1); // endpage 는 2. 근데 이걸 paging객체에 담아야쥐
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


    //일단 숫자까지 받아왔고. 그럼 이 로직을 어떻게 수행하냐면.
    //네비게이션에서 1을 누르면 1~10까지의 페이징처리
    // 10을눌러도 1~10까지의 페이징처리
    // 11을누르면 11~20까지의 페이징처리
    // 그니까 그걸 눌러서 들어오는 정보를 토대로 메서드 돌려서 네비게이션 출력을 다르게 해주는걸 만들면됨.

//@GetMapping("/{itemId}/edit")
//public String editForm(@PathVariable Long itemId, Model model) {


}




