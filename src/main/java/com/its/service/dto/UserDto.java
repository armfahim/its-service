package com.its.service.dto;

import com.its.service.entity.auth.User;
import com.its.service.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserDto {

    Long id;
    String username;
    Role role;

    public static UserDto from(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public void to(User user) {
        user.setId(id);
        user.setUsername(username);
        user.setRole(role);
    }
}
