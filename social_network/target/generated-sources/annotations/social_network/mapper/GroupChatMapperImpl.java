package social_network.mapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import social_network.dto.GroupChatDto;
import social_network.dto.UserDto;
import social_network.entity.GroupChat;
import social_network.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class GroupChatMapperImpl implements GroupChatMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public GroupChatDto doGroupChatDto(GroupChat groupChat) {
        if ( groupChat == null ) {
            return null;
        }

        Integer id = null;
        UserDto creator = null;
        Set<UserDto> users = null;

        id = groupChat.getId();
        creator = userMapper.doUserDto( groupChat.getCreator() );
        users = userSetToUserDtoSet( groupChat.getUsers() );

        GroupChatDto groupChatDto = new GroupChatDto( id, creator, users );

        return groupChatDto;
    }

    @Override
    public List<GroupChatDto> doGroupChatDtoList(List<GroupChat> groupChats) {
        if ( groupChats == null ) {
            return null;
        }

        List<GroupChatDto> list = new ArrayList<GroupChatDto>( groupChats.size() );
        for ( GroupChat groupChat : groupChats ) {
            list.add( doGroupChatDto( groupChat ) );
        }

        return list;
    }

    protected Set<UserDto> userSetToUserDtoSet(Set<User> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserDto> set1 = new LinkedHashSet<UserDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( User user : set ) {
            set1.add( userMapper.doUserDto( user ) );
        }

        return set1;
    }
}
