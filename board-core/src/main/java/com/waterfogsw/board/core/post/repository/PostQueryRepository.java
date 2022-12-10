package com.waterfogsw.board.core.post.repository;

import static com.waterfogsw.board.core.board.domain.QBoard.*;
import static com.waterfogsw.board.core.post.entity.QPost.*;
import static com.waterfogsw.board.core.user.domain.QUser.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waterfogsw.board.core.post.entity.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Optional<Post> getDetail(long id) {
    Post result = jpaQueryFactory.selectFrom(post)
                                 .where(post.id.eq(id))
                                 .join(post.board, board)
                                 .fetchJoin()
                                 .join(post.writer, user)
                                 .fetchJoin()
                                 .distinct()
                                 .fetchOne();

    return Optional.ofNullable(result);
  }

}
