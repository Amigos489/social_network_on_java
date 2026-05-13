package social_network.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import social_network.dto.ReceivedFriendInvitationDto;
import social_network.dto.SentFriendInvitationDto;
import social_network.entity.FriendInvitation;
import social_network.entity.User;
import social_network.primarykey.FriendInvitationPrimaryKey;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-13T23:07:54+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class FriendInvitationMapperImpl implements FriendInvitationMapper {

    @Override
    public SentFriendInvitationDto toSentFriendInvitation(FriendInvitation friendInvitation) {
        if ( friendInvitation == null ) {
            return null;
        }

        String senderName = null;
        String senderSurname = null;

        senderName = friendInvitationPrimaryKeySenderName( friendInvitation );
        senderSurname = friendInvitationPrimaryKeySenderSurname( friendInvitation );

        SentFriendInvitationDto sentFriendInvitationDto = new SentFriendInvitationDto( senderName, senderSurname );

        return sentFriendInvitationDto;
    }

    @Override
    public ReceivedFriendInvitationDto toReceivedFriendInvitation(FriendInvitation friendInvitation) {
        if ( friendInvitation == null ) {
            return null;
        }

        String recipientName = null;
        String recipientSurname = null;

        recipientName = friendInvitationPrimaryKeyRecipientName( friendInvitation );
        recipientSurname = friendInvitationPrimaryKeyRecipientSurname( friendInvitation );

        ReceivedFriendInvitationDto receivedFriendInvitationDto = new ReceivedFriendInvitationDto( recipientName, recipientSurname );

        return receivedFriendInvitationDto;
    }

    @Override
    public List<SentFriendInvitationDto> toSentFriendInvitation(List<FriendInvitation> friendInvitation) {
        if ( friendInvitation == null ) {
            return null;
        }

        List<SentFriendInvitationDto> list = new ArrayList<SentFriendInvitationDto>( friendInvitation.size() );
        for ( FriendInvitation friendInvitation1 : friendInvitation ) {
            list.add( toSentFriendInvitation( friendInvitation1 ) );
        }

        return list;
    }

    @Override
    public List<ReceivedFriendInvitationDto> toReceivedFriendInvitation(List<FriendInvitation> friendInvitation) {
        if ( friendInvitation == null ) {
            return null;
        }

        List<ReceivedFriendInvitationDto> list = new ArrayList<ReceivedFriendInvitationDto>( friendInvitation.size() );
        for ( FriendInvitation friendInvitation1 : friendInvitation ) {
            list.add( toReceivedFriendInvitation( friendInvitation1 ) );
        }

        return list;
    }

    private String friendInvitationPrimaryKeySenderName(FriendInvitation friendInvitation) {
        FriendInvitationPrimaryKey primaryKey = friendInvitation.getPrimaryKey();
        if ( primaryKey == null ) {
            return null;
        }
        User sender = primaryKey.getSender();
        if ( sender == null ) {
            return null;
        }
        return sender.getName();
    }

    private String friendInvitationPrimaryKeySenderSurname(FriendInvitation friendInvitation) {
        FriendInvitationPrimaryKey primaryKey = friendInvitation.getPrimaryKey();
        if ( primaryKey == null ) {
            return null;
        }
        User sender = primaryKey.getSender();
        if ( sender == null ) {
            return null;
        }
        return sender.getSurname();
    }

    private String friendInvitationPrimaryKeyRecipientName(FriendInvitation friendInvitation) {
        FriendInvitationPrimaryKey primaryKey = friendInvitation.getPrimaryKey();
        if ( primaryKey == null ) {
            return null;
        }
        User recipient = primaryKey.getRecipient();
        if ( recipient == null ) {
            return null;
        }
        return recipient.getName();
    }

    private String friendInvitationPrimaryKeyRecipientSurname(FriendInvitation friendInvitation) {
        FriendInvitationPrimaryKey primaryKey = friendInvitation.getPrimaryKey();
        if ( primaryKey == null ) {
            return null;
        }
        User recipient = primaryKey.getRecipient();
        if ( recipient == null ) {
            return null;
        }
        return recipient.getSurname();
    }
}
