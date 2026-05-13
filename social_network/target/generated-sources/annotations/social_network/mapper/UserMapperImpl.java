package social_network.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import social_network.dto.UserDto;
import social_network.dto.UserRegisterDto;
import social_network.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRegisterDto doUserRegisterDto(User user) {
        if ( user == null ) {
            return null;
        }

        String name = null;
        String surname = null;
        String login = null;
        String password = null;

        name = user.getName();
        surname = user.getSurname();
        login = user.getLogin();
        password = user.getPassword();

        UserRegisterDto userRegisterDto = new UserRegisterDto( name, surname, login, password );

        return userRegisterDto;
    }

    @Override
    public List<UserRegisterDto> doUserRegisterDtoList(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserRegisterDto> list = new ArrayList<UserRegisterDto>( user.size() );
        for ( User user1 : user ) {
            list.add( doUserRegisterDto( user1 ) );
        }

        return list;
    }

    @Override
    public UserDto doUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String surname = null;

        id = user.getId();
        name = user.getName();
        surname = user.getSurname();

        UserDto userDto = new UserDto( id, name, surname );

        return userDto;
    }

    @Override
    public List<UserDto> doUserDtoList(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( user.size() );
        for ( User user1 : user ) {
            list.add( doUserDto( user1 ) );
        }

        return list;
    }
}
