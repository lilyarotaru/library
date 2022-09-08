package org.example.library.controller;

import org.example.library.model.Book;
import org.example.library.service.BookService;
import org.example.library.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String getAll(@RequestParam(name = "sort", required = false) boolean sorted,
                         @RequestParam(name = "page", required = false) Integer page,
                         @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                         Model model) {
        model.addAttribute("books", bookService.findAll(page, booksPerPage, sorted));
        return "books/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "title", required = false) String name, Model model) {
        if (name != null) {
            model.addAttribute("books", bookService.findAllByTitleStartingWith(name));
        }
        return "books/search";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id, Model model) {
        Book book = bookService.findOne(id);
        if (book.getPerson() == null) {
            model.addAttribute("people", peopleService.findAll());
        }
        model.addAttribute("book", book);
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        bookService.returnToLibrary(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/give")
    public String give(@PathVariable("id") int id, @RequestParam("personId") int personId) {
        bookService.give(id, personId);
        return "redirect:/books/" + id;
    }
}