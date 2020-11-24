package com.company.service;

import com.qorb.domain.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Page<User> getUserPageable(Integer page, Integer pagesize);

    User saveUser(User user);

    User editUser(User user);

    Optional<User> getUser(Long idUser);
}
