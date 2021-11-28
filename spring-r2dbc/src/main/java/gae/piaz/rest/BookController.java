package gae.piaz.rest;

import gae.piaz.domain.dto.BookDTO;
import gae.piaz.repository.BookRepository;
import gae.piaz.service.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/books/")
@Slf4j
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper mapper;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BookDTO> getAll() {
        return Flux.just(UUID.randomUUID()).flatMap(uid -> {
            log.info("getAll() {} running", uid);
            return this.bookRepository.findAll().
                    map(mapper::toDto).doFinally((a) -> {
                        log.info("getAll() {} executed", uid);
                    });
        });
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Mono<BookDTO> saveBook(@RequestBody BookDTO dto) {
        return Mono.just(UUID.randomUUID()).flatMap(uid -> {
            log.info("saveBook() {} running", uid);
            return this.bookRepository.save(mapper.toEntity(dto))
                    .map(mapper::toDto).doFinally((s) -> {
                        log.info("saveBook() {} ", uid);
                    });
        });
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Mono<BookDTO> update(@RequestBody BookDTO dto) {
        return this.bookRepository.findByIsbn(dto.getIsbn()).flatMap(b -> {
            b.setAuthor(dto.getAuthor());
            return this.bookRepository.save(b).map(mapper::toDto);
        });
    }

}
