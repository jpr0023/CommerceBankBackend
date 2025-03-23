package com.example.demo.service;


import com.example.demo.domain.Users;
import com.example.demo.repository.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {

    UsersRepo usersRepo;

    public Users create(Users user) {
        return usersRepo.save(user);
    }

}
