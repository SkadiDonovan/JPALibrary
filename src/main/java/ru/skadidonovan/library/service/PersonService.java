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
public class PersonService {
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        return personRepository.findById(id).orElse(null);
    }

    public Optional<Person> findOneByName(String name, int id){
        return personRepository.findOneByNameAndIdNot(name, id);
    }

    public List<Book> findBooksByOwner(Person owner){
        return bookRepository.findByOwner(owner);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson) {
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }
}
