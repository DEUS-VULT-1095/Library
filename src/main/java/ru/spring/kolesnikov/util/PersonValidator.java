package ru.spring.kolesnikov.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spring.kolesnikov.models.Person;
import ru.spring.kolesnikov.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Person personInDb = peopleService.findAll().stream().filter(p -> p.getFullName()
                .equals(person.getFullName())).findAny().orElse(null);
        if (personInDb != null && personInDb.getPersonId() != person.getPersonId()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже зарегистрирован");
        }
    }
}
