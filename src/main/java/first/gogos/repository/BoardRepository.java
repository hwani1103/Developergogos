package first.gogos.repository;

import first.gogos.domain.Board;
import first.gogos.domain.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;
    public Long add(Board board){
        em.persist(board);
        return board.getId();
    }

    public int getCount(){
        Long singleResult = em.createQuery("select count(b) from Board b", Long.class).getSingleResult();
        return Integer.parseInt(String.valueOf(singleResult));
    }

    /**
     * 게시물이 20개까지는 네비게이션도 1개.
     * 21개부터는 2개.
     * 41개부터는 3개.
     * 61개부터는 4개.
     * 81 101 121 141 161 181~200
     * 5    6   7  8    9  10
     * 그래. 게시물이 200개보다 더 많으면 그냥 배열길이는 10으로 고정시키면 됨. 아닌데
     * 만약 201개있으면 또다시 1개보여줘야되는데 어허..
     * 1~20은 1
     * 21~40은 2
     * 41~60은 3
     * 61~80은 4
     * 81~100은 5
     * 101~120은 6
     * 121~140은 7
     * 141~160은 8
     * 161~180은 9
     * 181~200은 10
     * 다시
     * 201~220개 인데 11을 눌르면 네비게이션은 11 하나만 나와야 되는거다.
     * 그럼 거꾸로 확인하는게?
     * 11인데.
     *
     *
     *
     *
     *
     * 아.게시물이 200개면
     * navigation은 10까지만 보여야되네. 게시물이 200개인데 11로 안가야지.
     *
     *
     *
     * 자.. 여기서.
     *  private int navSize = 10; // 10
     *     public int[] nav = new int[navSize]; // 1~10, 11~20 이렇게 초기화해서 네비게이션 표현할 배열
     *     private int totalPost = 200; // 200
     *     private int totalPage = totalPost / navSize ;
     * 음..
     * 1.paging객체를 생성한다. navSize는 고정 10. 배열길이도 [navSize]인데?
     * 쿼리날려서 totalPost를 얻어온다.
     * 자. navSize는 고정이 아니다
     * if(count>=10) navSize = 10 10이상이면 10.
     * else navSize = count; 10미만이면 그대로. 그럼 navSize변수가 필요가 없는거같은데
     *
     *
     *
     *
     */

    public List<Board> findAll(int page) {
        return em.createQuery("select b from Board b order by b.id desc", Board.class)
                .setFirstResult(page * 20 - 19)
                .setMaxResults(20) // 1부터 20까지 가져오기.. ㅡ_ㅡ;;;;; 동적으로 어떻게??
                .getResultList();
    }
//        return em.createQuery("select b from Board b order by b.id desc", Board.class)
//                .getResultList(); // 전부 가져오기.

    // page 1 : 1~20           //뒤에는 page * 20
    // page 2 : 21 ~ 40         // 1 1
    // page 3 : 41 ~ 60         // 2 21
    // page 4 : 61 ~ 80         // 3 41
                                // 4 61
                                // 5 81

//    private List<Board> getResultList(int page) {
//        return em.createQuery("select b from Board b order by b.id desc", Board.class)
//                .setFirstResult(page * 20-19)
//                .setMaxResults(20) // 1부터 20까지 가져오기.. ㅡ_ㅡ;;;;; 동적으로 어떻게??
//                .getResultList();
//    }
}
//            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(20)
//                    .getResultList();
//            System.out.println("result.size() = " + result.size());
//            for (Member member : result) {
//                System.out.println("member = " + member.getUsername());
//            }