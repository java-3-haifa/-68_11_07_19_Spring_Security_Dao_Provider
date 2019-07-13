package com.telran.customauthprovider.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private ConcurrentHashMap<String,UserEntity> users = new ConcurrentHashMap<>();

    private PasswordEncoder encoder;

    public UserRepositoryImpl(PasswordEncoder encoder) {
        this.encoder = encoder;
        UserEntity admin = new UserEntity();
        admin.username = "admin@mail.com";
        admin.password = encoder.encode("password");
        admin.roles = new String[]{"ROLE_USER","ROLE_ADMIN"};
        users.put(admin.username,admin);
    }

    @Override
    public void addUser(String username, String password) {
        UserEntity entity = new UserEntity();
        entity.username = username;
        entity.password = encoder.encode(password);
        entity.roles = new String[]{"ROLE_USER"};
        if(users.putIfAbsent(username,entity) != null){
            throw new RuntimeException("User already exist");
        }
    }

    @Override
    public UserEntity getUser(String username) {
        return users.get(username);
    }

    @Override
    public void removeUser(String username) {
        users.remove(username);
    }

    @Override
    public List<String> getAll() {
        return users.values().stream()
                .map(user -> user.username + ":" + user.password + ":" + Arrays.toString(user.roles))
                .collect(Collectors.toList());
    }
}
