// эта информация поможет оптимизировать запросы при аутентификации пользователя
// https://stackoverflow.com/questions/56566406/spring-security-differentiate-between-wrong-username-password-combination-and
// https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html#match-by-dispatcher-type
package ru.tpchr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ru.tpchr.services.AuthorService;

import javax.servlet.DispatcherType;
import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthorService authorService;
    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                    .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .antMatchers("/", "/test", "/main/**", "/saveauthor", "/getComments/*", "/img/**", "/static/img/css/**", "/js/**", "/upload/**", "/reset/*", "/registration", "/check/**", "/static/**", "/activate/*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureHandler(authenticationFailureHandler())
                    .successHandler(customAuthenticationSuccessHandler())
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                .and()
                    .rememberMe()
                    .key("ghjtyughj")
                    .userDetailsService(authorService)
                    .tokenValiditySeconds(30*24*60*60)
                    .tokenRepository(tokenRepository());
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(authorService);
        authenticationProvider.setPreAuthenticationChecks(new AccountStatusUserDetailsChecker());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }
}



