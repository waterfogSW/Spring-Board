package com.watefogsw.board.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import com.watefogsw.board.oauth.service.dto.OAuthUserProfile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

  public static User from(OAuthUserProfile profile) {
    return User.builder()
               .email(profile.email())
               .name(profile.name())
               .imageUrl(profile.imageUrl())
               .oauthId(profile.oauthId())
               .role(Role.USER)
               .build();
  }

  public User update(OAuthUserProfile profile) {
    this.email = profile.email();
    this.name = profile.name();
    this.imageUrl = profile.imageUrl();
    return this;
  }

}
