package com.telran.customauthprovider.controller;

import com.telran.customauthprovider.repository.UserRepository;
import com.telran.customauthprovider.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public void registration(@RequestBody UserDto user){
        userRepository.addUser(user.username,user.password);
    }

    @DeleteMapping("{username}")
    public void delete(@PathVariable("username")String username){
        userRepository.removeUser(username);
    }

    @GetMapping("all")
    public List<String> getAll(){
        return userRepository.getAll();
    }

}
