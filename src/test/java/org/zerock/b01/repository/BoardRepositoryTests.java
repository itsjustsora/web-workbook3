package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title ..." + i)
                    .content("Content ..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: {}", result.getBno());
        });
    }

    @Test
    void testSelect() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info("board: {}", board);
    }

    @Test
    void testUpdate() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update title 100", "update content 100");

        boardRepository.save(board);
    }

    @Test
    void testDelete() {
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    @Test
    void testPaging() {
        // 1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: {}", result.getTotalElements());
        log.info("total pages: {}", result.getTotalPages());
        log.info("page number: {}", result.getNumber());
        log.info("page size: {}", result.getSize());

        List<Board> todoList = result.getContent();

        todoList.forEach(board -> log.info("board: {}", board));
    }

    @Test
    void testSearch1() {
        // 2 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }

    @Test
    void testSearchAll() {
        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

        // total pages
        log.info("result.getTotalPages(): {}", result.getTotalPages());

        // page size
        log.info("result.getSize(): {}", result.getSize());

        // page number
        log.info("result.getNumber(): {}", result.getNumber());

        // prev next
        log.info("result.hasPrevious(): {}, result.hasNext: {}", result.hasPrevious(), result.hasNext());

        result.getContent().forEach(board -> log.info("board: {}", board));

    }
}
