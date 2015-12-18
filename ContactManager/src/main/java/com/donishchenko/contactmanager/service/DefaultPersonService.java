package com.donishchenko.contactmanager.service;

import com.donishchenko.contactmanager.entity.Person;
import com.donishchenko.contactmanager.repository.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personService")
@Transactional(rollbackFor = Exception.class)
public class DefaultPersonService implements PersonService {

    @Autowired
    @Qualifier("personDao")
    private PersonDao personDao;

    @Override
    public boolean save(Person person) {
        try {
            personDao.save(person);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Person getById(Long id) {
        try {
            return personDao.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean delete(Person person) {
        return false;
    }
}
