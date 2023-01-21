package first.gogos.repository;

import first.gogos.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
public class BoardRepository {

    private final EntityManager em;

    public Long add(Board board){
        em.persist(board);
        return board.getId();
    }


}
