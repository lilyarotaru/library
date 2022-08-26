package org.example.repository;

import org.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findByName(String name);

    @Query("SELECT p from Person p left join fetch p.books where p.id=?1")
    Person findByIdWithBook(int id);        //one of the methods to avoid N+1 problem
}
