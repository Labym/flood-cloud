package com.labym.flood.config.model.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Table(name = "config_application")
public class Application {
    @Id
    private Long id;
    @Column(name = "application_name",length = 100,nullable = false)
    private String name;
    private Instant createAt;
}
