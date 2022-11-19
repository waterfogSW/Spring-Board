package com.watefogsw.board.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watefogsw.board.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String oauthId);

}
