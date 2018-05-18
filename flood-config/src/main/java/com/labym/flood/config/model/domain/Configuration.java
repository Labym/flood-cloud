package com.labym.flood.config.model.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "config_application_configuration",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"config_key","application_id","profile_id"})
        },
        indexes = {@Index(columnList = "application_id")}
)
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_key", length = 100, nullable = false)
    private String key;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "application_id")
    private Application application;
    @Column(name = "config_value")
    private String value;
}
