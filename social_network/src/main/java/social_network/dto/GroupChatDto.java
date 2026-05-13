package social_network.dto;

import java.util.Set;

public record GroupChatDto(
        Integer id,
        UserDto creator,
        Set<UserDto> users
) {}
