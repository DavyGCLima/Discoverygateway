package com.example.eureka_gateway.eureka_gateway.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_responsible_entity")
public class UserResponsibleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USERS_RESPONSIBLE_ENTITY")
    @SequenceGenerator(name = "SEQ_USERS_RESPONSIBLE_ENTITY", sequenceName = "SEQ_USERS_RESPONSIBLE_ENTITY", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "company_id")
    private Integer companyId;

}
