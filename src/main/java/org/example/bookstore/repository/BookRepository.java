package org.example.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.example.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    List<Book> findByAvailableCopiesGreaterThan(int availableCopies);

}
