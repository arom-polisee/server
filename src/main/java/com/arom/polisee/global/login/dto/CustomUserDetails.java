package com.arom.polisee.global.login.dto;

import com.arom.polisee.global.login.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;
    private final List<SimpleGrantedAuthority> authorities;

    @JsonCreator
    public CustomUserDetails(@JsonProperty("user") UserEntity userEntity,
                             @JsonProperty("authorities") List<String> roles) {
        this.userEntity = userEntity;
        this.authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.authorities = List.of(new SimpleGrantedAuthority(userEntity.getRole()));
    }

    @JsonProperty("authorities")
    public List<String> getAuthoritiesAsString() {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }
    public Long getId() {
        return userEntity.getId();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
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
        return true;
    }
}
