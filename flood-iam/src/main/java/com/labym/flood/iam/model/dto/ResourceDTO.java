package com.labym.flood.iam.model.dto;

import com.labym.flood.common.constant.ResourceType;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import com.labym.flood.common.util.tree.Node;
import com.labym.flood.iam.config.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ResourceDTO implements Node<Long> {

  @ApiModelProperty(readOnly = true)
  private Long id;

  private String name;

  private String url;

  private String code;

  private Long parentId;

  private ResourceType type;

  @ApiModelProperty(readOnly = true)
  private Instant createAt;
  @ApiModelProperty(hidden = true)
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
    return null==this.parentId|| Objects.equals(Constants.ROOT_RESOURCE_ID,this.parentId);
  }
}
