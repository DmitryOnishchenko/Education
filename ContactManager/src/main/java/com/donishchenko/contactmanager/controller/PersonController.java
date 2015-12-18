package com.donishchenko.contactmanager.controller;

import com.donishchenko.contactmanager.entity.Person;
import com.donishchenko.contactmanager.service.PersonService;
import com.donishchenko.contactmanager.utils.Ajax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/person")
public class PersonController extends ExceptionHandlerController {

    @Autowired
    @Qualifier("personService")
    private PersonService personService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> getPersonById(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return Ajax.emptyResponse();
            }

            Person person = personService.getById(id);
            return Ajax.successResponse(person);
        } catch (Exception e) {
            return Ajax.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> savePerson(@RequestParam("data") String data) {
        try {
            if (data == null || data.equals("")) {
                return Ajax.emptyResponse();
            }

            Person person = new Person(data, data, data);
            personService.save(person);

            return Ajax.successResponse(person);
        } catch (Exception e) {
            return Ajax.errorResponse(e.getMessage());
        }
    }
}
