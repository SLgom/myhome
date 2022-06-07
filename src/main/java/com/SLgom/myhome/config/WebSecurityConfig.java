package com.SLgom.myhome.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    //누구나 접근 가능한 url 설정
                    .antMatchers("/", "/account/register", "/css/**").permitAll()
                    //위에서 설정한 경로url를 제외한 모든 접근을 막는다는 뜻
                    .anyRequest().authenticated()
                    .and()
                //로그인 페이지 설정 = 위에서 설정한 접근가능 페이지가 아닌 페이지에 접근 할 경우 로그인 페이지로
                .formLogin()
                    .loginPage("/account/login")
                    .permitAll() // 로그인 페이지 접근가능하도록
                    .and()
                .logout()
                    .permitAll();
    }
    //의존성 주입
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder()) //패스워드 인코딩
                //인증처리
                .usersByUsernameQuery("select username, password, enabled "
                        + "from user "
                        + "where username = ?")
                //권한처리
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role  r on ur.role_id = r.id "
                        + "where u.username = ?");
    }
    //패스워드 인코더
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}