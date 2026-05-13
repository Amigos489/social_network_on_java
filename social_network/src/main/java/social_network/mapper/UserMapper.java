package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import social_network.dto.UserDto;
import social_network.dto.UserRegisterDto;
import social_network.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    public UserRegisterDto doUserRegisterDto(User user);

    public List<UserRegisterDto> doUserRegisterDtoList(List<User> user);

    public UserDto doUserDto(User user);

    public List<UserDto> doUserDtoList(List<User> user);
}
