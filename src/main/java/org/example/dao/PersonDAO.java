package org.example.dao;

import org.example.models.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        return people;
    }

    public Person get(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        Hibernate.initialize(person.getBooks());
        return person;
    }

    public Optional<Person> getByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p from Person p WHERE name=:name")
                .setParameter("name", name).getResultList().stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(person);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person personToRemove = session.get(Person.class, id);
        session.remove(personToRemove);
    }
}
