package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SuccessUserHandler successUserHandler;






    // SuccessHandler это обработчик успешной аутентификации
    // UserDetails - минимальная информация о пользователях (логин, пароль и тд)

//    @Autowired
//    public WebSecurityConfig(SuccessUserHandler successUserHandler
//            , @Qualifier("userServiceImpl") UserDetailsService userDetailsService) {
//        this.successUserHandler = successUserHandler;
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }
    @Bean
    @Qualifier("userServiceImpl")
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // конфиги в которых указывается доступы пользователей
        http
                .cors().disable()
                .csrf().disable() //  защита от CSRF-атак( типо подставного сайта где злоумышленник его использует и заставляет
                // от имени пользователя отправлять пароли, деньги со счёта на счёт и т.п
                .authorizeRequests() //авторизацуем запрос
                .antMatchers("/login", "/").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") //прописываем доступ для юрл /user/**
                .antMatchers("/admin/**").hasRole("ADMIN") //прописываем доступ для юрл /admin/**
                .anyRequest().authenticated() // все запросы должны быть авторизованы и аутентифицированы
                .and()
                .formLogin() // задаю форму для ввода логина-пароля, по дефолту это "/login"
                .successHandler(successUserHandler)
                .permitAll() // доступно всем
                .and()
                .logout().permitAll(); // настройка логаута
    }
}