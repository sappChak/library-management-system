package org.example.bookstore.repository;

import java.util.List;

import org.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Page<Book> findByAvailableCopiesGreaterThan(int availableCopies, Pageable pageable);

    List<Book> findByTitleContainingIgnoreCase(String query);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleQuery, String authorQuery);

    Long countByAvailableCopiesGreaterThan(int availableCopies);
}
