package com.norwayyachtbrockers.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"user_id", "user_email", "user_first_name", "user_last_name", "user_role"})
public class UserResponseDto {

    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("user_email")
    private String email;

    @JsonProperty("user_first_name")
    private String firstName;

    @JsonProperty("user_last_name")
    private String lastName;

    @JsonProperty("user_role")
    private String roleName;
}
