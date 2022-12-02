package com.waterfogsw.board.core.board.repository;

import static com.waterfogsw.board.core.board.domain.QBoard.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

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

  public List<Board> getSliceOfBoard(
      @Nullable
      Long id,
      int size,
      @Nullable
      String keyword
  ) {
    return jpaQueryFactory.selectFrom(board)
                          .where(
                              ltBoardId(id),
                              containsTitle(keyword),
                              containsDescription(keyword)
                          )
                          .orderBy(board.id.desc())
                          .limit(size)
                          .fetch();
  }

  private BooleanExpression containsTitle(@Nullable String title) {
    return hasText(title) ? board.title.containsIgnoreCase(title) : null;
  }

  private BooleanExpression containsDescription(@Nullable String description) {
    return hasText(description) ? board.description.containsIgnoreCase(description) : null;
  }

  private BooleanExpression ltBoardId(@Nullable Long id) {
    return id == null ? null : board.id.lt(id);
  }

}
