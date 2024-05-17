package com.example.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.backend.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("users")
@ApiModel("用户实体类")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userId;
    private Integer userType;
    private String username;
    private String openId;
    private String avatar;
    private Integer isNew;
    private String address;
    private String telephone;






    public static User of(UserDTO userDTO){

        return User.builder()
                .userId(userDTO.getUserId())
                .userType(userDTO.getUserType())
                .username(userDTO.getUsername())
                .openId(userDTO.getOpenId())
                .avatar(userDTO.getAvatar())
                .isNew(userDTO.getIsNew())
                .address(userDTO.getAddress())
                .telephone(userDTO.getTelephone())
                .build();
    }
}
