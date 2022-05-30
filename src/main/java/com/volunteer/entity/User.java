package com.volunteer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("user")
@TableName("user")
public class User implements Serializable{
    @TableId(value = "openid", type = IdType.INPUT)
    private String openid;

    @TableField(value = "session_key")
    private String sessionKey;

    @TableField(value = "nickname")
    @ApiModelProperty("用户昵称")
    private String nickname;

    @TableField(value = "password")
    @ApiModelProperty("用户密码")
    private String password;

    @TableField(value = "icon")
    @ApiModelProperty("用户头像")
    private String icon;

//    @TableField(value = "gender")
//    @ApiModelProperty("用户性别")
//    private Boolean gender;

    @TableField(value = "create_time")
    @ApiModelProperty("用户创建时间")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    @ApiModelProperty("用户更新时间")
    private LocalDateTime updateTime;
}
