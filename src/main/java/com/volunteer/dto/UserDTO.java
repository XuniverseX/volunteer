package com.volunteer.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String openid;
    private String sessionKey;
    private String icon;
    private String nickname;
    private boolean isAuth;
}
