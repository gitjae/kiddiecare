package com.spring.kiddiecare.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findUserById(String id);

    public List<User> findAllById(String id);

    public Optional<User> findUserByIdAndIsValid(String id, boolean valid);
}
