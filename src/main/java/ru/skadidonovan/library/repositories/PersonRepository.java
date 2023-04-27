package ru.skadidonovan.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skadidonovan.library.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findOneByNameAndIdNot(String name, int id);

    @Query(
            value = "SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id = ?1",
            nativeQuery = true)
    Optional<Person> findOwnerById(int id);

}
