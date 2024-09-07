package org.news.aggregator.service;


import org.news.aggregator.entity.LoginDto;
import org.news.aggregator.entity.RegistrationToken;
import org.news.aggregator.entity.UserInfo;
import org.news.aggregator.entity.UserDto;
import org.news.aggregator.repository.RegistrationTokenRepository;
import org.news.aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RegistrationTokenRepository regTokenRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserInfo registerUser(UserDto userDto){
        UserInfo user = UserInfo.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .isEnabled(false)
                .fName(userDto.getUsername())
                .userId(userDto.getUserId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
       return userRepository.save(user);
    }

    @Override
    public List<UserInfo> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void createVerificationToken(UserInfo userDetails, String token){
        regTokenRepo.save(new RegistrationToken(token, userDetails));
    }

    @Override
    public boolean validateTokenAndEnableUser(String token) {
        RegistrationToken verificationToken = regTokenRepo.findByToken(token);
        if (verificationToken == null) {
            return false;
        }
        if (verificationToken.getExpirationTime().getTime() > System.currentTimeMillis()) {
            UserInfo user = verificationToken.getUser();
            if (!user.getIsEnabled()) {
                user.setIsEnabled(true);
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public UserInfo authenticateUser(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow();
    }
}
