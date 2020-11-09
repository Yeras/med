package kz.med.service.impl;

import kz.med.model.User;
import kz.med.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    RegistrationRepository registrationRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = registrationRepository.findByEmailIdIgnoreCase(login);

        return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), new ArrayList<>());
    }

}