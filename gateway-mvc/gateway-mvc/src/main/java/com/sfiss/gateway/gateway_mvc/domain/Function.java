package com.sfiss.gateway.gateway_mvc.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * A Function entity.
 */
@Entity
@Table(name = "functions")
@Getter
@Setter
public class Function implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String name;
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

}
