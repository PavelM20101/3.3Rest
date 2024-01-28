package ru.itsinfo.springbootsecurityusersbootstrap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import ru.itsinfo.springbootsecurityusersbootstrap.config.handler.SuccessUserHandler;
import ru.itsinfo.springbootsecurityusersbootstrap.service.AppService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    // сервис, с помощью которого тащим пользователя
    private final AppService appService;

    private final PasswordEncoder passwordEncoder;

    // класс, в котором описана логика перенаправления пользователей по ролям

//    private final SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler;
//
//    // класс, в котором описана логика при неудачной авторизации
//    private final SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;
//
//    // класс, в котором описана логика при удачной авторизации
//    private final SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler;
//
//    // класс, в котором описана логика при отказе в доступе
//    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public ApplicationSecurityConfig(SuccessUserHandler successUserHandler,
                                     AppService appServiceTmp,
                                     PasswordEncoder passwordEncoder/*,
                                     SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler,
                                     SimpleUrlAuthenticationFailureHandler authenticationFailureHandler,
                                     SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler,
                                     AccessDeniedHandler accessDeniedHandler*/) {
        this.successUserHandler = successUserHandler;
        this.appService = appServiceTmp;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//        this.authenticationFailureHandler = authenticationFailureHandler;
//        this.urlLogoutSuccessHandler = urlLogoutSuccessHandler;
//        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //todo
                .authorizeRequests()
                .antMatchers("/", "index", "/css/**", "/js/**", "/webjars/**", "/actuator/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                /*.exceptionHandling().accessDeniedHandler(accessDeniedHandler)*/;
        http.formLogin()
                .loginPage("/") // указываем страницу с формой логина
                .permitAll()  // даем доступ к форме логина всем
                .successHandler(successUserHandler) //указываем логику обработки при удачном логине
                /*.failureHandler(authenticationFailureHandler) //указываем логику обработки при неудачном логине*/
                .usernameParameter("email") // Указываем параметры логина и пароля с формы логина
                .passwordParameter("password");
        http.logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/?logout")
                /*.logoutSuccessHandler(urlLogoutSuccessHandler)*/
                .permitAll();
    }
}