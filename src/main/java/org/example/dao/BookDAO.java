package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;
    static final BeanPropertyRowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM book", rowMapper);
    }

    public Book get(int id) {
        Book bookWithPerson = jdbcTemplate.query("SELECT * FROM book LEFT JOIN person on book.person_id = person.id WHERE book.id=?",
                new Object[]{id}, rs -> {
                    Book book1 = null;
                    while (rs.next()) {
                        int bookId = rs.getInt("id");
                        String title = rs.getString("title");
                        String author = rs.getString("author");
                        int year = rs.getInt("year_of_release");
                        String personIdFK = rs.getString("person_id");
                        if (personIdFK!=null){
                            int personId = Integer.parseInt(personIdFK);
                            String name = rs.getString("name");
                            int yearOfBirth = rs.getInt("year_of_birth");
                            Person person = new Person(personId, name, yearOfBirth);
                            book1 = new Book(bookId, title, author, year, person);
                        } else {
                            book1 = new Book(bookId, title, author, year, null);
                        }
                    }
                    return book1;
                });
        return bookWithPerson;
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year_of_release=? WHERE id=?", book.getTitle(),
                book.getAuthor(), book.getYearOfRelease(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year_of_release, person_id) VALUES (?,?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYearOfRelease(), null);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void give(int id, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personId, id);
    }

    public void returnToLibrary(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", null, id);
    }
}
