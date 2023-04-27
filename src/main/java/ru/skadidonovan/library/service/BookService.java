package ru.skadidonovan.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skadidonovan.library.models.Book;
import ru.skadidonovan.library.models.Person;
import ru.skadidonovan.library.repositories.BookRepository;
import ru.skadidonovan.library.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll(){
       return bookRepository.findAll();
    }

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public Optional<Person> findBookOwner(int id){
        return personRepository.findOwnerById(id);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person person) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(person);
        bookRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
        bookRepository.save(book);
    }
}
