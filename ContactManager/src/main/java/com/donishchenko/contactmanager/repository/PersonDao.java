package com.donishchenko.contactmanager.repository;

import com.donishchenko.contactmanager.entity.Person;

public interface PersonDao {

    void save(Person person);
    Person getById(Long id);
    void delete(Person person);

}
