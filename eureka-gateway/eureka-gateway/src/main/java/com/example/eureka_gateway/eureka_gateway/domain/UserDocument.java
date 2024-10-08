package com.example.eureka_gateway.eureka_gateway.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * User Document entity
 */
@Entity
@Table(name = "user_documents")
@Getter
@Setter
public class UserDocument implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOCUMENT_USER")
    @SequenceGenerator(name = "SEQ_DOCUMENT_USER", sequenceName = "SEQ_DOCUMENT_USER", allocationSize = 1)
    private Integer id;

    @Column(name = "document_file_name")
    private String name;

    @Column(name = "file")
    private String base64;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
