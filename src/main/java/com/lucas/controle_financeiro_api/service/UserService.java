package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.CreateUserDTO;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public CreateUserDTO createUser(CreateUserDTO data) {
        User newUser = this.repository.save(new User(null, data.name(), data.email(), data.password()));
        return new CreateUserDTO(data.name(), data.email(), data.password());
    }

    public User findUserById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}
