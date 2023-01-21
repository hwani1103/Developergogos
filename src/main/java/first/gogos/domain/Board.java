package first.gogos.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Board {

    @Id @GeneratedValue()
    private Long id; // 게시물 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String title; // 게시물 제목

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory; // 게시판 범주

    private String content; // 게시물 내용

    private int viewCnt; // 조회수

    private LocalDateTime writeTime; // 작성 시간

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    //setMember를 호출할 수 있는건 board니까.
    //멤버의 보드리스트에 board를 추가.
    //

}
