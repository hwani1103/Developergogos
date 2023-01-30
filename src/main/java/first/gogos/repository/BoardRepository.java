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

    public Board findOne(Long id){
        return em.find(Board.class, id);
    }

    public Long add(Board board) {
        em.persist(board);
        return board.getId();
    }

    public int getCount() {
        Long singleResult = em.createQuery("select count(b) from Board b", Long.class).getSingleResult();
        return Integer.parseInt(String.valueOf(singleResult));
    }

    public List<Board> findAll(int page) {
        return em.createQuery("select b from Board b order by b.id desc", Board.class)
                .setFirstResult(page * 20 - 20)
                .setMaxResults(20) // 1부터 20까지 가져오기.. ㅡ_ㅡ;;;;; 동적으로 어떻게??
                .getResultList();
    }
    public Board update(Board update){ // update는 쿼리 결과가 있는게 아니니까 executeupdate로 하고 반환값은 영향바은 행 수 int값임.
        em.createQuery("update Board b set b.title =: updateTitle, b.content =: updateContent where b.id =: id")
                .setParameter("updateTitle", update.getTitle())
                .setParameter("updateContent", update.getContent())
                .setParameter("id",update.getId())
                .executeUpdate();
        return update;
    }

    public void delete(Long id){
        em.createQuery("delete from Board b where b.id =: id")
                .setParameter("id", id)
                .executeUpdate();
    }

}


/**
 * String qlString = "update Product p " +
 *  "set p.price = p.price * 1.1 " +
 *  "where p.stockAmount < :stockAmount";
 * int resultCount = em.createQuery(qlString)
 *  .setParameter("stockAmount", 10)
 *  .executeUpdate();
 */

//