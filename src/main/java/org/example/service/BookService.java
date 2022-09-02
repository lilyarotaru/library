package org.example.service;

import org.example.model.Book;
import org.example.model.Person;
import org.example.repository.BookRepository;
import org.example.repository.PeopleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(Integer page, Integer books_per_page, boolean sorted) {
        if (page != null && books_per_page != null) {
            return sorted ? bookRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("yearOfRelease")))
                    .getContent() : bookRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
        }
        return sorted ? bookRepository.findAll(Sort.by("yearOfRelease")) : bookRepository.findAll();
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAllByTitleStartingWith(String title) {
        return bookRepository.findAllByTitleStartingWith(title);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Optional<Book> oldBook = bookRepository.findById(id);
        oldBook.ifPresent(value->{
            value.setTitle(book.getTitle());
            value.setAuthor(book.getAuthor());
            value.setYearOfRelease(book.getYearOfRelease());
        });
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void returnToLibrary(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setPerson(null);
            book.setBorrowDate(null);
        });
    }

    @Transactional
    public void give(int id, int personId) {
        Person person = peopleRepository.findById(personId).orElse(null);
        bookRepository.findById(id).ifPresent(book -> {
            book.setPerson(person);
            book.setBorrowDate(new Date());
        });
    }
}
