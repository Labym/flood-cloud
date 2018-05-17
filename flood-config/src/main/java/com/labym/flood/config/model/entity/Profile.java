package com.labym.flood.config.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "config_application_profile")
public class Profile {
    @Id
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "application_id")
    private Application application;
    private String name;
}
