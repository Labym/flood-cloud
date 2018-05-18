package com.labym.flood.config.model.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "config_application_profile")
public class Profile {
    @Id
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "application_id",nullable = false)
    private Application application;
    @Column(name = "profile_name",length = 100,nullable = false)
    private String name;
}
