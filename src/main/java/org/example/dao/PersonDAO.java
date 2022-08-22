package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final BeanPropertyRowMapper<Person> rowMapper = BeanPropertyRowMapper.newInstance(Person.class);

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", rowMapper);

    }

    public Person get(int id) {
        Person person = jdbcTemplate.query(
                        "SELECT * FROM person WHERE id=?", new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
        List<Book> books = jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id}, BookDAO.rowMapper);
        if (!books.isEmpty()) {
            person.setBooks(books);
        }
        return person;
    }

    public Optional<Person> getByName(String name) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE name=?", new Object[]{name}, rowMapper)
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, year_of_birth) VALUES (?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, year_of_birth=? WHERE id=?",
                person.getName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
