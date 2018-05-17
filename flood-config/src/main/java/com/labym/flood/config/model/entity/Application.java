package com.labym.flood.config.model.entity;


import lombok.Data;

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
    private String name;
    private Instant createAt;
}
