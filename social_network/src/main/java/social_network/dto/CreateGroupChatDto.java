package social_network.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateGroupChatDto(
        @NotNull(message = "creator id can't be null.")
        @Positive(message = "creator id must be positive number.")
        Integer creatorId,
        @NotNull(message = "user ids can't be null.")
        @NotEmpty(message = "user ids can't be empty.")
        List<Integer> userIds
) {}
