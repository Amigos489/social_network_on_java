package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import social_network.dto.MessageDto;
import social_network.entity.Message;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(source = "message.sender.name", target = "senderName")
    @Mapping(source = "message.sender.surname", target = "senderSurname")
    public MessageDto doMessageDto(Message message);

    public List<MessageDto> doMessageDtoList(List<Message> messages);
}
