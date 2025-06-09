package com.api.authservicejavaspringangular.controllers.dtos;

import com.api.authservicejavaspringangular.models.user.UserRole;
import com.api.authservicejavaspringangular.models.user.UserStatus;

import java.time.LocalDateTime;

public record UserOutPut(String id, String name, String email, String phone, String login, UserRole role,
                         UserStatus status, LocalDateTime createdAt) {

}
