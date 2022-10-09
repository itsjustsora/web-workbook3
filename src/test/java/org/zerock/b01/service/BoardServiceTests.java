package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user00")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("bno: {}", bno);
    }

    @Test
    void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Update... 101")
                .content("Update... 101")
                .build();

        boardService.modify(boardDTO);
    }
}
