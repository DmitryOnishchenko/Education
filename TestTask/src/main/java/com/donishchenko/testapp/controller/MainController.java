package com.donishchenko.testapp.controller;

import com.donishchenko.testapp.utils.JsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    @RequestMapping(value = "/private", method = RequestMethod.GET)
    public Map<String, Object> getPrivateKey(
            @RequestParam("master_key") String masterKey,
            @RequestParam("chain_code") String chainCode) {

        return JsonResponse.successResponse("");
    }

}
