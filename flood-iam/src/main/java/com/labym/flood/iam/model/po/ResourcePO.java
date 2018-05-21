package com.labym.flood.iam.model.po;

import com.labym.flood.common.constant.ResourceType;
import com.labym.flood.common.converter.MapJsonJpaConverter;
import com.labym.flood.processor.annotation.DTO;
import com.labym.flood.processor.annotation.EnableCodeGenerator;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;

@EnableCodeGenerator
@DTO
@Data
@Entity
@Table(name = "IAM_RESOURCE")
public class ResourcePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 1000)
    private String url;
    @Column(length = 100)
    private String code;
    private Long parentId;
    @Enumerated(EnumType.STRING)
    private ResourceType type;
    private Instant createAt;
    private Long createBy;

    @Lob
    @Convert(converter = MapJsonJpaConverter.class)
    private Map<String, Object> extensions;
}
