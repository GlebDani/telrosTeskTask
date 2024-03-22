package ru.danilenko.telros.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }


    @Transactional
    public void add(User user){
        if (user == null) {
            LOGGER.log(Level.SEVERE,
                    "no user defined");
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void update(User user){
        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public Optional<User> loadUserByEmail(String email){
        return userRepository.findByEmail(email);
    }






}
