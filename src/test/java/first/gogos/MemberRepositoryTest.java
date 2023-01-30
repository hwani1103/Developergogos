//package first.gogos;
//
//import first.gogos.domain.Board;
//import first.gogos.domain.Member;
//import first.gogos.repository.BoardRepository;
//import first.gogos.repository.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MemberRepositoryTest {
//
//    @Autowired
//    BoardRepository boardRepository;
//
//    @Test
//    public void findAll(){
//        Member member = new Member();
//        member.setEmail("asdasd");
//        Board board = new Board();
//        board.setContent("123123");
//        board.setMember(member);
//
//        List<Board> all = boardRepository.findAll();
//        Assertions.assertThat(all).isNotNull();
//
//
//    }
//
//
//}