package ru.skadidonovan.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skadidonovan.library.models.Book;
import ru.skadidonovan.library.models.Person;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);
}