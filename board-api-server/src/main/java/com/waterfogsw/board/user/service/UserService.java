package com.waterfogsw.board.user.service;

import org.springframework.stereotype.Service;

import com.waterfogsw.board.common.exception.NotFoundException;
import com.waterfogsw.board.core.user.domain.User;
import com.waterfogsw.board.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getUser(long userId) {
    return userRepository.findById(userId)
                         .orElseThrow(() -> new NotFoundException("User not found"));
  }

}
