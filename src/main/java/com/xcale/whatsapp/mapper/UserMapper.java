package com.xcale.whatsapp.mapper;

import com.xcale.whatsapp.model.User;
import com.xcale.whatsapp.model.UserRequest;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toUser(UserRequest source);
}
