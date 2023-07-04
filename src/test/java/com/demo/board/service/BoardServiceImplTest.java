package com.demo.board.service;

import com.demo.board.dto.BoardDTO;
import com.demo.board.dto.PageRequestDTO;
import com.demo.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService service;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("Test")
                .content("Test content")
                .writerEmail("user100@aaa.com")
                .writerName("user100")
                .build();

        Long bno = service.register(dto);

        System.out.println(bno);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = service.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100L;

        BoardDTO boardDTO = service.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 1L;

        service.removeWithReplies(bno);
    }

    @Test
    public void testModify() {
        BoardDTO dto = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        service.modify(dto);
    }
}