package org.news.aggregator.service;


import org.news.aggregator.entity.LoginDto;
import org.news.aggregator.entity.UserInfo;
import org.news.aggregator.entity.UserDto;

import java.util.List;


public interface UserService {
    UserInfo registerUser(UserDto userDto);
    List<UserInfo> getAllUsers();

    void createVerificationToken(UserInfo userDetails, String token);

    boolean validateTokenAndEnableUser(String token);
    public UserInfo authenticateUser(LoginDto loginDto);
}
