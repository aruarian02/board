package com.demo.board.service;

import com.demo.board.dto.ReplyDTO;
import com.demo.board.entity.Board;
import com.demo.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO dto);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO dto);
    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO dto) {
        Board board = Board.builder().bno(dto.getBno()).build();

        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
    }

    default ReplyDTO entityToDto(Reply entity) {
        return ReplyDTO.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

    }
}
