package org.example.library.repository;

import org.example.library.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @EntityGraph(attributePaths = {"person"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAllByTitleIgnoreCaseStartingWith(String name);

    @EntityGraph(attributePaths = {"person"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Book> findById(int id);
}