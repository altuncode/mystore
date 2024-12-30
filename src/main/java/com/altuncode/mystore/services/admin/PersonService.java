package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.Person;
import com.altuncode.mystore.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("PersonService")
public class PersonService {

    private final PersonRepo personRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public PersonService(@Qualifier("PersonRepo") PersonRepo personRepo, @Qualifier("PasswordEncoderBean") PasswordEncoder passwordEncoder) {
        this.personRepo = personRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Person registerUser(String email, String rawPassword) {
        Person person = new Person();
        person.setEmail(email);
        person.setPassword(passwordEncoder.encode(rawPassword));
        person.setRole("ROLE_USER");
        return personRepo.save(person);
    }

    public Person findByEmail(String username) {
        return personRepo.findByEmail(username).orElse(null);
    }

}
