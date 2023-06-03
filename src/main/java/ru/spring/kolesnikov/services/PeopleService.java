package ru.spring.kolesnikov.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.kolesnikov.models.Book;
import ru.spring.kolesnikov.models.Person;
import ru.spring.kolesnikov.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPersonId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Book> findBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        List<Book> books = null;
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            books = person.get().getBooks();
            for (Book book : books) {
                if (new Date().getTime() - book.getCollectionTime().getTime() > 864_000_000) {
                    book.setOverdue(true);
                }
            }
        }

        return books;
    }

    public Optional<Person> findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }
}
