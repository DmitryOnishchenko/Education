package com.donishchenko.contactmanager.service;

import com.donishchenko.contactmanager.entity.Person;

public interface PersonService {

    boolean save(Person person);
    Person getById(Long id);
    boolean delete(Person person);

}
