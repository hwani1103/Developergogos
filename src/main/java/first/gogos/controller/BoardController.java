package first.gogos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/list")
    public String board(){
        return "boardlist";
    }

    @GetMapping("/board/number")
    public String boardNumber(){
        return "posting";
    }

}
