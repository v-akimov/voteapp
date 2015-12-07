package ru.akimov.voteapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by z003cptz on 03.12.2015.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USER",
        indexes = {
                @Index(name = "IDX_USERNAME", columnList = "LOGIN", unique = true)
        })
public class User extends Identifiable implements UserDetails {

    @Column(name = "LOGIN")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @Column(name = "VOTE_DATE")
    @Temporal(TemporalType.DATE)
    private Date voteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTORANT_ID")
    private Restorant vote;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
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
