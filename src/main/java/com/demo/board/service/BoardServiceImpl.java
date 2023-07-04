package com.demo.board.service;

import com.demo.board.dto.BoardDTO;
import com.demo.board.dto.PageRequestDTO;
import com.demo.board.dto.PageResultDTO;
import com.demo.board.entity.Board;
import com.demo.board.entity.Member;
import com.demo.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository repository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (e -> entityToDto((Board)e[0], (Member) e[1], (Long) e[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board result = repository.getReferenceById(boardDTO.getBno());

        result.setTitle(boardDTO.getTitle());
        result.setContent(boardDTO.getContent());

        repository.save(result);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        // 댓글 부터 삭제
        repository.deleteByBno(bno);
        repository.deleteById(bno);
    }
}
