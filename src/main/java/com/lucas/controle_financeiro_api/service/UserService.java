package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.user.UserDTO;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // CREATE NEW USER
    public UserDTO createUser(UserDTO data) {
        User newUser = this.repository.save(new User(null, data.name(), data.email(), data.password()));
        return new UserDTO(data.name(), data.email(), data.password());
    }

    // FIND USER BY ID
    public User findUserById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
