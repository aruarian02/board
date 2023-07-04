package com.demo.board.service;

import com.demo.board.dto.BoardDTO;
import com.demo.board.dto.PageRequestDTO;
import com.demo.board.dto.PageResultDTO;
import com.demo.board.entity.Board;
import com.demo.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    BoardDTO get(Long bno);
    void modify(BoardDTO boardDTO);
    void removeWithReplies(Long bno);

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }

    default BoardDTO entityToDto(Board board, Member member, Long replyCount) {
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
    }
}
