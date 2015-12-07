package ru.akimov.voteapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.akimov.voteapp.dao.UserRepository;
import ru.akimov.voteapp.domain.Role;

/**
 * Created by z003cptz on 30.11.2015.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void configureGlobalAuthentication(AuthenticationManagerBuilder auth,
                                              UserDetailsService userDetailsService) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);

        auth.authenticationProvider(authenticationProvider);
    }


    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/restorants/*/menu").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/restorants/*/menu").hasAuthority(Role.ADMIN.name())
                .antMatchers("/**").authenticated()
                .and().csrf().disable();
    }
}
