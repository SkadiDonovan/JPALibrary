package ru.skadidonovan.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.skadidonovan.library.models.Person;
import ru.skadidonovan.library.service.PeopleService;


@Component
public class PeopleValidator implements Validator {

    private final PeopleService peopleService;

    public PeopleValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.getPersonByName(person.getName(), person.getId()).isPresent()){
            errors.rejectValue("name", "", "Читатель с таким ФИО уже существует");
        }
    }
}
