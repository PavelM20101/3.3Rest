package ru.pavel.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSecurityUsersBootstrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityUsersBootstrapApplication.class, args);
        /*
        * Логин - admin@mail.com
        * Пароль - admin
        *
        *Логин - user@mail.com
        *Пароль - user
        * */
    }
}
