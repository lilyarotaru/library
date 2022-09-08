package org.example.library.util;

import org.example.library.model.Person;
import org.example.library.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        Person existed = peopleRepository.findByName(person.getName());
        if (existed != null && existed.getId() != person.getId()) {
            errors.rejectValue("name", "", "Человек с таким именем уже зарегистрирован");
        }
    }
}
