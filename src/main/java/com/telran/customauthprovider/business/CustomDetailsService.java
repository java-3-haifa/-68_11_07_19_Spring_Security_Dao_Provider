package com.telran.customauthprovider.business;

import com.telran.customauthprovider.repository.UserEntity;
import com.telran.customauthprovider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomDetailsService implements UserDetailsService {

    private UserRepository repository;

    public CustomDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user =  repository.getUser(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new User(user.username,
                user.password,
                AuthorityUtils.createAuthorityList(user.roles));
    }
}
