package gae.piaz.performance.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "books")
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
@Getter
@Setter
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "book_id")
    private Integer bookId;

    private String author;

    private String isbn;

    private String title;

    private Integer year;

    @OneToMany(mappedBy = "book")
    private List<Order> orders;

}