package com.sfiss.gateway.gateway_mvc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Column(name = "name")
    private String name;

    @Column(name = "main_representative")
    private String mainRepresentative;

    @Column(name = "last_name")
    private String lastName;

    @Id
    @Column(name = "id_number")
    private Integer id;

    @Column(name = "code_number")
    private String codeNumber;

    @Column(name = "id_primary_user")
    private Integer primaryUserId;

}
