package com.labym.flood.iam.model.dto;

import com.labym.flood.common.constant.ResourceType;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.time.Instant;
import java.util.Map;

import com.labym.flood.common.util.tree.Node;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO implements Node<Long> {
  private Long id;

  private String name;

  private String url;

  private String code;

  private Long parentId;

  private ResourceType type;

  private Instant createAt;

  private Long createBy;

  private Map<String, Object> extensions;

  @Override
  public Long id() {
    return this.id;
  }

  @Override
  public Long parentId() {
    return this.parentId;
  }

  @Override
  public boolean isRoot() {
    return this.id==this.parentId;
  }
}
