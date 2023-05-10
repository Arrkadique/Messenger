package com.arrkadique.springmessenger.repository;

import com.arrkadique.springmessenger.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
