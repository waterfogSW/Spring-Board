package com.waterfogsw.board.core.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import com.waterfogsw.board.core.common.entity.BaseTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "`user`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String name;

  private String imageUrl;

  @NotNull
  @Column(unique = true)
  private String oauthId;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Builder
  public User(
      String email,
      String name,
      String imageUrl,
      String oauthId,
      Role role
  ) {
    this.email = email;
    this.name = name;
    this.imageUrl = imageUrl;
    this.oauthId = oauthId;
    this.role = role;
  }

  public User update(
      String email,
      String name,
      String imageUrl
  ) {
    this.email = email;
    this.name = name;
    this.imageUrl = imageUrl;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User)o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

}
