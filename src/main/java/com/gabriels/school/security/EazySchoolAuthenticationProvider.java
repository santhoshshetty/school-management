package com.gabriels.school.security;


import com.gabriels.school.model.Person;
import com.gabriels.school.model.Roles;
import com.gabriels.school.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EazySchoolAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        Person person = personRepository.readByEmail(email);
        if (person != null && person.getPersonId() > 0 && passwordEncoder.matches(password, person.getPwd())) {
            return new UsernamePasswordAuthenticationToken(person.getEmail(), password, getGrantedAuthorities(person.getRoles()));
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    public List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //When this is provided, it will execute the above authenticate method where the username, password approach is resolved for authentication
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
