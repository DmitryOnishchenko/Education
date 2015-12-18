package com.donishchenko.contactmanager.repository;

import com.donishchenko.contactmanager.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("personDao")
public class HibernatePersonDao implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Override
    public Person getById(Long id) {
        return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Override
    public void delete(Person person) {
    }
}
