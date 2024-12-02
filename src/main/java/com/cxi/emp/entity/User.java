package com.cxi.emp.entity;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cxi.emp.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String organization;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false)
  private Boolean emailVerified;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;
  @Column(name = "verification_token")
  private String verificationToken;
  @Column(name = "token_expiry")
  private LocalDateTime tokenExpiry;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return emailVerified != null && emailVerified;
    // return true;
  }

  public Integer getId() {
    return id;
  }
  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", organization=" + organization + ", password="
    + password + ", emailVerified=" + emailVerified + ", role=" + role + ", verificationToken=" + verificationToken
    + ", tokenExpiry=" + tokenExpiry + "]";
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public boolean isEmailVerified() {
    return emailVerified;
  }
  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }
  public UserRole getRole() {
    return role;
  }
  public void setRole(UserRole role) {
    this.role = role;
  }
  public String getOrganization() {
    return organization;
  }
  public void setOrganization(String companyName) {
    this.organization = companyName;
  }
  public Boolean getEmailVerified() {
    return emailVerified;
  }
  public void setEmailVerified(Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }
  public String getVerificationToken() {
    return verificationToken;
  }
  public void setVerificationToken(String verificationToken) {
    this.verificationToken = verificationToken;
  }
  public LocalDateTime getTokenExpiry() {
    return tokenExpiry;
  }
  public void setTokenExpiry(LocalDateTime tokenExpiry) {
    this.tokenExpiry = tokenExpiry;
  }
}
