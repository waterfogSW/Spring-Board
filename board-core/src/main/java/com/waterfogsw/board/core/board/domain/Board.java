package com.waterfogsw.board.core.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.waterfogsw.board.core.common.entity.BaseTime;
import com.waterfogsw.board.core.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20)
  private String title;

  @Column(length = 200)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private User creator;

  @Builder
  public Board(
      String title,
      String description,
      User creator
  ) {
    this.title = title;
    this.description = description;
    this.creator = creator;
  }

}
