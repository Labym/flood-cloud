package com.labym.flood.iam.model.dto;

import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;

  private String login;

  private String password;

  private String firstName;

  private String lastName;

  private String email;

  private Boolean activated;

  private String langKey;

  private String imageUrl;

  private String activationKey;

  private String resetKey;

  private Instant resetDate;

  private String salt;
}
