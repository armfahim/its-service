package com.its.service.response;

import com.its.service.entity.auth.User;
import com.its.service.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserResponse {
    Long id;
    String username;
    Role role;

    public static UserResponse from(User user) {
        UserResponse dto = new UserResponse();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public void to(User user) {
        user.setId(id);
        user.setUsername(username);
        user.setRole(role);
    }
}
