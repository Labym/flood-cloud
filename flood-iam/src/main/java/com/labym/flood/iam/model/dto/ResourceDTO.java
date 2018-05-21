package com.labym.flood.iam.model.dto;

import com.labym.flood.common.constant.ResourceType;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {
  private Long id;

  private String name;

  private String url;

  private String code;

  private Long parentId;

  private ResourceType type;

  private Instant createAt;

  private Long createBy;

  private Map<String, Object> extensions;
}
