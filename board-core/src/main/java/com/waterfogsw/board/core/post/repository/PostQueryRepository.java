package com.waterfogsw.board.core.post.repository;

import static com.waterfogsw.board.core.board.domain.QBoard.*;
import static com.waterfogsw.board.core.post.entity.QPost.*;
import static com.waterfogsw.board.core.user.domain.QUser.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
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

  public List<Post> getSlice(
      @Nullable
      Long id,
      long boardId,
      int size,
      @Nullable
      String keyword
  ) {
    return jpaQueryFactory.selectFrom(post)
                          .where(ltPostId(id), search(keyword))
                          .join(post.writer, user)
                          .fetchJoin()
                          .join(post.board, board)
                          .fetchJoin()
                          .where(post.board.id.eq(boardId))
                          .orderBy(post.id.desc())
                          .limit(size)
                          .fetch();
  }

  @SuppressWarnings("all")
  private BooleanExpression search(String keyword) {
    return hasText(keyword) ? containsTitle(keyword).or(containsContent(keyword)) : null;
  }

  private BooleanExpression containsTitle(@Nullable String title) {
    return hasText(title) ? post.title.contains(title) : null;
  }

  private BooleanExpression containsContent(@Nullable String contents) {
    return hasText(contents) ? post.content.contains(contents) : null;
  }

  private BooleanExpression ltPostId(@Nullable Long id) {
    return id == null ? null : post.id.lt(id);
  }

}
