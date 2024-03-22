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

    private AuthInfoService authInfoService;

    public UserDetailsServiceImpl(UserRepository userRepository, AuthInfoService authInfoService) {
        this.userRepository = userRepository;
        this.authInfoService = authInfoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
         Optional<User> user = userRepository.findByEmail(username);
         if(user.isEmpty())
             throw new UsernameNotFoundException("Нет пользователя с таким email");
        System.out.println(user.get());
         return new UserDetailsImp(user.get(), authInfoService);
    }
}
