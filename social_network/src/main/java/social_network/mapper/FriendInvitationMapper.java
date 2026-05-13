package social_network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import social_network.dto.ReceivedFriendInvitationDto;
import social_network.dto.SentFriendInvitationDto;
import social_network.entity.FriendInvitation;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FriendInvitationMapper {

    @Mapping(source = "primaryKey.sender.name", target = "senderName")
    @Mapping(source = "primaryKey.sender.surname", target = "senderSurname")
    public SentFriendInvitationDto toSentFriendInvitation(FriendInvitation friendInvitation);

    @Mapping(source = "primaryKey.recipient.name", target = "recipientName")
    @Mapping(source = "primaryKey.recipient.surname", target = "recipientSurname")
    public ReceivedFriendInvitationDto toReceivedFriendInvitation(FriendInvitation friendInvitation);

    public List<SentFriendInvitationDto> toSentFriendInvitation(List<FriendInvitation> friendInvitation);

    public List<ReceivedFriendInvitationDto> toReceivedFriendInvitation(List<FriendInvitation> friendInvitation);
}
