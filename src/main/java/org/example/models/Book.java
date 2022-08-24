package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @NotEmpty
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 chars")
    private String title;

    @Column(name = "author")
    @NotEmpty
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 chars")
    private String author;

    @Column(name = "year_of_release")
    @NotNull
    @Min(value = 1000, message = "Year of release must be greater than 1000")
    private int yearOfRelease;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book() {
    }

    public Book(String title, String author, int yearOfRelease, Person person) {
        this.title = title;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + yearOfRelease;
    }
}
