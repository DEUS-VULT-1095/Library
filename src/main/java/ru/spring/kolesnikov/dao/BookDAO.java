//package ru.spring.kolesnikov.dao;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import ru.spring.kolesnikov.models.Book;
//import ru.spring.kolesnikov.models.Person;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class BookDAO {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public BookDAO(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Book> index() {
//        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
//    }
//
//    public Book show(int id) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[] {id},
//                        new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
//    }
//
//    public void save(Book book) {
//        jdbcTemplate.update("INSERT INTO Book(title, author, year) VALUES (?, ?, ?)",
//                book.getTitle(), book.getAuthor(), book.getYear());
//    }
//
//    public void update(int id, Book updatedBook) {
//        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? WHERE book_id=?", updatedBook.getTitle(),
//                updatedBook.getAuthor(), updatedBook.getYear(), id);
//    }
//
//    public void delete(int id) {
//        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
//    }
//
//    public void release(int id) {
//        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", null, id);
//    }
//
//    public Optional<Person> getBookOwner(int id) {
//        return jdbcTemplate.query("SELECT Person.* FROM Book INNER JOIN Person ON Book.person_id = Person.person_id" +
//                " WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
//    }
//
//    public void assign(int id, Person person) {
//        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person.getPersonId(), id);
//    }
//}
