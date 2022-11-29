package com.waterfogsw.board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterfogsw.board.core.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
