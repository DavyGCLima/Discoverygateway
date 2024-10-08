package com.example.eureka_gateway.eureka_gateway.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

/**
 * A Module entity.
 */
@Entity
@Table(name = "module")
@Getter
@Setter
public class Module implements Serializable {

    @Id
    private Integer id;

    private String name;

    private String token;

    @JsonIgnore
    @OneToMany(mappedBy = "module", orphanRemoval = true, cascade = CascadeType.ALL)
    @BatchSize(size = 20)
    private Set<Function> functions = new HashSet<>();

}
