package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import social_network.dto.GroupChatDto;
import social_network.entity.GroupChat;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface GroupChatMapper {

    public GroupChatDto doGroupChatDto(GroupChat groupChat);

    public List<GroupChatDto> doGroupChatDtoList(List<GroupChat> groupChats);
}
