package com.gabriels.school.service;

import com.gabriels.school.SchoolConstants;
import com.gabriels.school.model.Person;
import com.gabriels.school.model.Roles;
import com.gabriels.school.repository.PersonRepository;
import com.gabriels.school.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean savePerson(Person person){
        boolean isSaved=false;
        Roles role=rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        personRepository.save(person);
        if(person.getPersonId()>0)
            isSaved=true;
        return isSaved;
    }
}
