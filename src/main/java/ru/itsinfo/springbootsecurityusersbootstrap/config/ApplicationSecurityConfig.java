package ru.itsinfo.springbootsecurityusersbootstrap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itsinfo.springbootsecurityusersbootstrap.config.handler.SuccessUserHandler;
import ru.itsinfo.springbootsecurityusersbootstrap.service.AppService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    private final AppService appService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(SuccessUserHandler successUserHandler,
                                     AppService appServiceTmp,
                                     PasswordEncoder passwordEncoder) {
        this.successUserHandler = successUserHandler;
        this.appService = appServiceTmp;
        this.passwordEncoder = passwordEncoder;
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