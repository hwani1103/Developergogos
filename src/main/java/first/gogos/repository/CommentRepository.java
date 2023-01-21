package first.gogos.repository;

import first.gogos.domain.Board;
import first.gogos.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommentRepository {

    private final EntityManager em;

    public Long add(Comment comment){
        em.persist(comment);
        return comment.getId();
    }


}
