package com.example.eureka_gateway.eureka_gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 * A Profile entity.
 */
@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile implements Serializable {

    public static final String ACTIVE_STATUS = "ACTIVE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    @SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE", allocationSize = 1)
    private Integer id;
    private String name;
    @Size(max = 10)
    private String status;

    @Column(name = "internal_user")
    private Boolean internalUser;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "profiles_functions",
        joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},

        inverseJoinColumns = {@JoinColumn(name = "function_id", referencedColumnName = "id")})
    @BatchSize(size = 20)
    private Set<Function> functions = new HashSet<>();

}
