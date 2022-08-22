package org.example.models;

import javax.validation.constraints.*;
import java.util.List;

public class Person {

    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should between 2 and 30 chars")
    private String name;

    @NotNull
    @Min(value = 1930, message = "Year of birth must be greater than 1930")
    @Max(value = 2022, message = "Year of birth cannot be in future")
    private int yearOfBirth;

    private List<Book> books;

    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return name + ", " + yearOfBirth;
    }
}
