package com.waterfogsw.board.core.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waterfogsw.board.core.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
