package com.SLgom.myhome.service;

import com.SLgom.myhome.model.Role;
import com.SLgom.myhome.model.User;
import com.SLgom.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1l); //권한 1번 id를 가져옴
        user.getRoles().add(role);

        return userRepository.save(user);
    }

}
