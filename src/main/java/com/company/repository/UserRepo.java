package com.company.repository;

import com.company.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
    //   Page<Person> getByFilter(Pageable pageRequest, GridFilter filter);
    @Query("select r from User r ")
    Page<User> getAll(Pageable pageable);
}

