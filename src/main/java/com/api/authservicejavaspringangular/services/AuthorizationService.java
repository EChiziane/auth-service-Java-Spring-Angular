package com.api.authservicejavaspringangular.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.api.authservicejavaspringangular.models.user.User;
import com.api.authservicejavaspringangular.controllers.dtos.UserInput;
import com.api.authservicejavaspringangular.controllers.dtos.UserOutPut;
import com.api.authservicejavaspringangular.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public List<UserOutPut> getUsers() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).
                stream()
                .map(this::mapToUserOutput)
                .collect(Collectors.toList());
    }


    private UserOutPut mapToUserOutput(User user) {
        return user.UserOutPut();
    }


    public void deleteUser(UUID id) {
        repository.deleteById(id.toString());
    }
@Transactional
    public Optional<UserOutPut> userUpdate(UUID id, UserInput userInput) {
        return repository.findById(id.toString())
                .map(existingUser->{
                    existingUser.setEmail(userInput.email());
                    existingUser.setPhone(userInput.phone());
                    existingUser.setRole(userInput.role());
                    existingUser.setStatus(userInput.status());
                    User updatedUser=repository.save(existingUser);
                    return  mapToUserOutput(updatedUser);
                });
    }
}
