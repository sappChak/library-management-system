package org.example.library.repository;

import java.util.List;

import org.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    Long countByAvailableCopiesGreaterThan(int availableCopies);

    List<Book> findByTitleContainingIgnoreCase(String query);

    Page<Book> findByAvailableCopiesGreaterThan(int availableCopies, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleQuery, String authorQuery,
            Pageable pageable);
}
