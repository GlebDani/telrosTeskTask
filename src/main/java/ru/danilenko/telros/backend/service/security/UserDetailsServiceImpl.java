package ru.danilenko.telros.backend.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.entity.security.UserDetailsImp;
import ru.danilenko.telros.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;



    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> user = userRepository.findByEmail(username);
         if(user.isEmpty())
             throw new UsernameNotFoundException("Нет пользователя с таким email");
         return new UserDetailsImp(user.get());
    }
}
