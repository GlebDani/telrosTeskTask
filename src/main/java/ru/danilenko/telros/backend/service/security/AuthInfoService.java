package ru.danilenko.telros.backend.service.security;

import org.springframework.stereotype.Service;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.entity.security.AuthInfo;
import ru.danilenko.telros.backend.repository.AuthInfoRepository;
import ru.danilenko.telros.backend.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthInfoService {

    private final long EXPIRED_TIME = 60;

    private AuthInfoRepository authInfoRepository;
    private UserRepository userRepository;

    public AuthInfoService(AuthInfoRepository authInfoRepository, UserRepository userRepository) {
        this.authInfoRepository = authInfoRepository;
        this.userRepository = userRepository;
    }

    public boolean isNonExpired(String email){
        System.out.println("I am here");
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty() )
            return false;
        User user1 = user.get();
        if(authInfoRepository.findById(user1.getId()).isEmpty())
            return true;
        System.out.println("auth_time"+ authInfoRepository.findById(user1.getId()).get().getAuthTime().getTime());
        System.out.println(System.currentTimeMillis()*1000);
        return authInfoRepository.findById(user1.getId()).get().getAuthTime().getTime()*1000+ EXPIRED_TIME < System.currentTimeMillis()*1000;

    }
    public void save(String email){
        User user = userRepository.findByEmail(email).get();
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUser_id(user.getId());
        authInfo.setAuthTime(new Date());
        authInfoRepository.save(authInfo);
    }
}
