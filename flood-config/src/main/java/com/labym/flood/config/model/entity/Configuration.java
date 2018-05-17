package com.labym.flood.config.model.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "config_application_profile")
public class Configuration {

    @Id
    private Long id;

    private String key;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "application_id")
    private Application application;

    private String value;
}
