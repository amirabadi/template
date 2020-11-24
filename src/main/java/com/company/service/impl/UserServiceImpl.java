package com.company.service.impl;

import com.qorb.domain.User;
import com.qorb.repository.UserRepo;
import com.qorb.service.PageEntity;
import com.qorb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends PageEntity implements UserService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Page<User> getUserPageable(Integer page, Integer pagesize) {
        Pageable pageRequest = createPageRequest(page , pagesize);
        Page<User> userPage = userRepo.getAll(pageRequest);

        return userPage;
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public Optional<User> getUser(Long idUser) {
        return Optional.empty();
    }
}
