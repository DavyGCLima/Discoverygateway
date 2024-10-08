package com.example.eureka_gateway.eureka_gateway.domain;

import com.example.eureka_gateway.eureka_gateway.util.SecurityUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * A user.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
    @SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", allocationSize = 1)
    private Integer id;

    @JsonIgnore
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 255)
    @Column(length = 255, unique = true)
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 30)
    @NotNull
    @Column(name = "legal_number", length = 30)
    private String legalNumber;

    @Size(max = 30)
    @Column(name = "staff_number", length = 30)
    private String staffNumber;

    @NotNull
    private Integer type = 0;

    @NotNull
    private String status;

    @OneToMany(mappedBy = "user")
    private Set<Permission> permissions = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "user_profiles",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")})

    @BatchSize(size = 20)
    private Set<Profile> profiles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<UserDocument> documents = new HashSet<>();

    public Set<GrantedAuthority> getAuthorities() {

        if (permissions != null && !permissions.isEmpty()) {

            Set<GrantedAuthority> authorities = permissions.stream()
                    .filter(permission -> Profile.ACTIVE_STATUS.equals(permission.getProfile().getStatus()))
                    .flatMap(permission -> permission.getProfile().getFunctions().stream())
                    .map(func -> new SimpleGrantedAuthority("ROLE_" + func.getToken()))
                    .collect(Collectors.toSet());

            if (SecurityUtils.INTERNAL_USER.equals(this.getType())) {
                authorities.add(new SimpleGrantedAuthority(SecurityUtils.ROLE_INTERNAL_USER));
                return authorities;
            }

            authorities.add(new SimpleGrantedAuthority(SecurityUtils.ROLE_EXTERNAL_USER));
            return authorities;
        }

        return Collections.emptySet();
    }

}
