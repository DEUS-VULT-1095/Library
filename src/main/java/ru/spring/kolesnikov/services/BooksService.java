package ru.spring.kolesnikov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.kolesnikov.repositories.BooksRepository;
import ru.spring.kolesnikov.models.Book;
import ru.spring.kolesnikov.models.Person;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return booksRepository.findAll();
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> findByTitleStartingWith(String startingWith) {
        if (startingWith.equals("")) {
            return null;
        }
        return booksRepository.findByTitleStartingWith(startingWith);
    }

    public Person findBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setBookId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        updatedBook.setCollectionTime(bookToBeUpdated.getCollectionTime());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.setCollectionTime(null);
            book.setOverdue(false);
            book.setOwner(null);
        }
    }

    @Transactional
    public void assign(int id, Person owner) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book != null) {
            book.setCollectionTime(new Date());
            book.setOwner(owner);
        }
    }
}
