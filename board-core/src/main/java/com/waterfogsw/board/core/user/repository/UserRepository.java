package com.waterfogsw.board.core.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterfogsw.board.core.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByOauthId(String oauthId);

}
