package com.waterfogsw.board.core.post.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.waterfogsw.board.core.board.domain.Board;
import com.waterfogsw.board.core.common.entity.BaseTime;
import com.waterfogsw.board.core.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  private User writer;

  @Builder
  public Post(
      String title,
      String content,
      Board board,
      User writer
  ) {
    this.title = title;
    this.content = content;
    this.board = board;
    this.writer = writer;
  }

}
