package com.example.bookstoreserver.mapper;

import com.example.bookstoreserver.dto.user.UserDto;
import com.example.bookstoreserver.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
//@RequiredArgsConstructor
public class UserMapper {
//    private final Optional<ModelMapper> modelMapper;
//
//    public UserDto toDto(Optional<User> user) {
//        return modelMapper.map(mapper -> mapper.map(user, UserDto.class)).orElse(null);
//    }
    public UserDto toUserDto(User user){
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setFullName(user.getFullName());
        tmp.setUsername(user.getUsername());
        tmp.setEmail(user.getEmail());
        tmp.setRoles(user.getRoles());
        tmp.setCart(user.getCart());

        return tmp;
    }
}
