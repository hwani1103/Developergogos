package first.gogos.service;

import first.gogos.domain.Board;
import first.gogos.domain.Paging;
import first.gogos.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long addPosting(Board board) {
        return boardRepository.add(board);
    }

    public List<Board> getList(int page){
        return boardRepository.findAll(page); }

    public int getCount(){
        return boardRepository.getCount();
    }

    public Board read(Long id){
        return boardRepository.findOne(id);
    }

    public Board update(Board update){
        return boardRepository.update(update);
    }

    public void delete(Long id){
        boardRepository.delete(id);
    }

//    public Paging paging(){
//        return boardRepository.paging();
//    }
}
