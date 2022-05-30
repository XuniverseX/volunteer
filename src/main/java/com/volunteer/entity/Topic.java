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
@ApiModel("topic")
@TableName("topic")
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户openId")
    private String userId;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String icon;

    @ApiModelProperty("用户姓名")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty("是否点赞")
    @TableField(exist = false)
    private Boolean isLike;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标题照片")
    private String images;

    @ApiModelProperty("话题内容")
    private String content;

    @ApiModelProperty("点赞数量")
    private Integer liked;

    @ApiModelProperty("评论数量")
    private Integer comments;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
