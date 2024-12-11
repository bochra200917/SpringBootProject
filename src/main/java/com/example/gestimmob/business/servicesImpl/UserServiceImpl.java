package com.example.gestimmob.business.servicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gestimmob.business.services.UserService;
import com.example.gestimmob.dao.entities.User;
import com.example.gestimmob.dao.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException(
                    "User with this email address '" + user.getEmail() + "' already exists");
        }
        String passwd = user.getPassword();
        String encodedPasswod = passwordEncoder.encode(passwd);
        user.setPassword(encodedPasswod);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findUserByEmail(email);
        org.springframework.security.core.userdetails.User springUser = null;
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        User user = optUser.get();
        List<String> roles = user.getRoles();
        Set<GrantedAuthority> ga = new HashSet<>();
        for (String role : roles) {
            ga.add(new SimpleGrantedAuthority(role));
        }
        springUser = new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                ga);
        return springUser;
    }
}