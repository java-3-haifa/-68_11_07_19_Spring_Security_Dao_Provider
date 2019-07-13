package com.telran.customauthprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("message")
public class MessageController {

    @GetMapping
    public String getMessage(Principal principal){
        return principal.getName() + " messages";
    }
}
