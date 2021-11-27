package gae.piaz.rest;

import gae.piaz.domain.dto.BookDTO;
import gae.piaz.repository.BookRepository;
import gae.piaz.service.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/books/")
public class BookController {

    @Autowired
    private BookRepository fruitRepository;

    @Autowired
    private BookMapper mapper;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BookDTO> getAll() {
        return this.fruitRepository.findAll()
                .map(mapper::toDto);
    }

//	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public Mono<ResponseEntity<Book>> getFruit(@PathVariable String name) {
//		return this.fruitRepository.findByIsbn(name)
//			.map(ResponseEntity::ok)
//			.defaultIfEmpty(ResponseEntity.notFound().build());
//	}
//
//	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@Transactional
//	public Mono<Book> addFruit(@Valid @RequestBody Book fruit) {
//		return this.fruitRepository.save(fruit);
//	}
}
