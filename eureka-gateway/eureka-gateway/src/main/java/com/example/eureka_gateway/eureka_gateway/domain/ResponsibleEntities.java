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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "responsible_entities")
public class ResponsibleEntities implements Serializable {

    @Id
    @Column(name = "id_number", unique = true, nullable = false)
    @SequenceGenerator(allocationSize = 1, sequenceName = "seq_responsible_entities", name = "seq_responsible_entities")
    @GeneratedValue(generator = "seq_responsible_entities", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "main_representative")
    private String mainRepresentative;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "code_number")
    private String codeNumber;

    @Column(name = "id_primary_user")
    private Integer primaryUserId;

}
