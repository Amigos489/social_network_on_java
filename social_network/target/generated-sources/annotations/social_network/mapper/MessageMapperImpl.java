package social_network.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import social_network.dto.MessageDto;
import social_network.entity.Message;
import social_network.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto doMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        String senderName = null;
        String senderSurname = null;
        String text = null;
        LocalDateTime dateTimeSending = null;

        senderName = messageSenderName( message );
        senderSurname = messageSenderSurname( message );
        text = message.getText();
        dateTimeSending = message.getDateTimeSending();

        MessageDto messageDto = new MessageDto( text, senderName, senderSurname, dateTimeSending );

        return messageDto;
    }

    @Override
    public List<MessageDto> doMessageDtoList(List<Message> messages) {
        if ( messages == null ) {
            return null;
        }

        List<MessageDto> list = new ArrayList<MessageDto>( messages.size() );
        for ( Message message : messages ) {
            list.add( doMessageDto( message ) );
        }

        return list;
    }

    private String messageSenderName(Message message) {
        User sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        return sender.getName();
    }

    private String messageSenderSurname(Message message) {
        User sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        return sender.getSurname();
    }
}
