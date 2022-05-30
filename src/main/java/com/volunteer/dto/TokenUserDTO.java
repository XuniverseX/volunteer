package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenUserDTO {
    private UserDTO user;
    private String token;
}
