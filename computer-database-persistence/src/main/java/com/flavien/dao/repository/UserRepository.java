package com.flavien.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flavien.models.User;

public interface UserRepository extends JpaRepository<User, String> {}