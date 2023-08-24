package com.spring.kiddiecare.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findUserById(String id);

    public Optional<User> findUserByIdAndIsValid(String id, boolean valid);

    public Optional<User> findUserByPhone(int phone);

    public Optional<User> findUserByPhone(String phone);

    public Optional<User> findUserByNameAndPhone(String name, int phone);

    public Optional<User> findUserByNameAndPhone(String name, String phone);

    public Optional<User> findUserByIdAndNameAndPhone(String id, String name, int phone);

    public Optional<User> findUserByIdAndNameAndPhone(String id, String name, String phone);

    public User findUserByNo(int no);


}
