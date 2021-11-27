package gae.piaz.repository;

import gae.piaz.domain.Book;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

	Mono<Book> findByIsbn(String name);

}
