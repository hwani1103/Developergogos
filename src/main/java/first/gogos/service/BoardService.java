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
//    public Paging paging(){
//        return boardRepository.paging();
//    }
}
