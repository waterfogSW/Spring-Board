package com.waterfogsw.board.core.board.repository;

import static com.waterfogsw.board.core.board.domain.QBoard.*;

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

  public List<Board> getPageOfBoard(
      @Nullable
      Long id,
      int pageSize
  ) {
    return jpaQueryFactory.selectFrom(board)
                          .where(ltBoardId(id))
                          .orderBy(board.id.desc())
                          .limit(pageSize)
                          .fetch();
  }

  private BooleanExpression ltBoardId(@Nullable Long id) {
    return id == null ? null : board.id.lt(id);
  }

}
