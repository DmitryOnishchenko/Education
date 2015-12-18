package com.donishchenko.contactmanager.controller;

import com.donishchenko.contactmanager.utils.Ajax;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String, Object> handleException(Exception e) {
        e.printStackTrace();
        return Ajax.errorResponse(e.getMessage());
    }
}
