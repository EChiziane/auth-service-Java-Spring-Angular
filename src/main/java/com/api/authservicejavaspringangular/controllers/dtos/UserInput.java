package com.api.authservicejavaspringangular.controllers.dtos;


import com.api.authservicejavaspringangular.models.user.User;
import com.api.authservicejavaspringangular.models.user.UserRole;
import com.api.authservicejavaspringangular.models.user.UserStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record UserInput(String name, String email, String phone, String password, UserRole role, UserStatus status) {

    public User toUser() {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        return new User( encryptedPassword, role, email, phone, name, status);
    }


}