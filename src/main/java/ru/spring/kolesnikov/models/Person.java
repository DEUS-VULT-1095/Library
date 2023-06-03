package ru.spring.kolesnikov.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;
    @Pattern(regexp = "[A-ZА-Я][a-zа-я]* [A-ZА-Я][a-zа-я]* [A-ZА-Я][a-zа-я]*",
        message = "ФИО должно соответствовать формату: Фамилия Имя Отчество")
    @Column(name = "full_name")
    private String fullName;
    @Min(value = 1900, message = "Дата рождения должна быть не менее 1900 года")
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {
    }

    public Person(String name, int year) {
        this.fullName = name;
        this.yearOfBirth = year;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
