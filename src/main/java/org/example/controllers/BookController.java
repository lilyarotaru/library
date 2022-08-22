package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("books", bookDAO.getAll());
        return "books/books";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.get(id);
        if (book.getPerson()==null){
            model.addAttribute("people", personDAO.getAll());
        }
        model.addAttribute("book", book);
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.get(id));
        return "books/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int id){
        bookDAO.returnToLibrary(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/give")
    public String give(@PathVariable("id")int id, @RequestParam("personId") int personId){
        bookDAO.give(id, personId);
        return "redirect:/books/"+id;
    }
}
