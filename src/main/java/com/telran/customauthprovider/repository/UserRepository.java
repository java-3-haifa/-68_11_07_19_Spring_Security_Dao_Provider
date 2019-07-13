package com.telran.customauthprovider.repository;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository {
    void addUser(String username, String password);
    UserEntity getUser(String username);
    void removeUser(String username);
    List<String> getAll();
}
