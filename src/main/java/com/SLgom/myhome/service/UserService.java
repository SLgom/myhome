package com.SLgom.myhome.service;

import com.SLgom.myhome.model.Role;
import com.SLgom.myhome.model.User;
import com.SLgom.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//비즈니스 로직 관련
//회원가입시 패스워드를 인코딩하고 , 권한을 설정하는 클래스 이다
@Service
public class UserService {

    @Autowired //를 이용해 유저 객체를 받아온다
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    //회원가입
    public User save(User user){
        //회원가입시 패스워트 인코딩
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true); //회원가입시 활성화
        //권한 설정
        Role role = new Role();
        role.setId(1l); //권한 1번 id를 가져옴
        user.getRoles().add(role);

        return userRepository.save(user);
    }

}
