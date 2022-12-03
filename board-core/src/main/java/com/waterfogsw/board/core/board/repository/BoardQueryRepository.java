package com.waterfogsw.board.core.board.repository;

import static com.waterfogsw.board.core.board.domain.QBoard.*;
import static com.waterfogsw.board.core.user.domain.QUser.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waterfogsw.board.core.board.domain.Board;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Optional<Board> getDetail(long id) {
    Board result = jpaQueryFactory.selectFrom(board)
                                  .where(board.id.eq(id))
                                  .join(board.creator, user)
                                  .fetchJoin()
                                  .distinct()
                                  .fetchOne();

    return Optional.ofNullable(result);
  }

  public List<Board> getSliceOfBoard(
      @Nullable
      Long id,
      int size,
      @Nullable
      String keyword
  ) {
    return jpaQueryFactory.selectFrom(board)
                          .where(ltBoardId(id), search(keyword))
                          .orderBy(board.id.desc())
                          .limit(size)
                          .fetch();
  }

  @SuppressWarnings("all")
  private BooleanExpression search(String keyword) {
    return hasText(keyword) ? containsTitle(keyword).or(containsDescription(keyword)) : null;
  }

  private BooleanExpression containsTitle(@Nullable String title) {
    return hasText(title) ? board.title.contains(title) : null;
  }

  private BooleanExpression containsDescription(@Nullable String description) {
    return hasText(description) ? board.description.contains(description) : null;
  }

  private BooleanExpression ltBoardId(@Nullable Long id) {
    return id == null ? null : board.id.lt(id);
  }

}
