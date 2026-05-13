package social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePostInCommunityDto(
        @NotBlank(message = "content must not be empty or null")
        String content,

        @NotNull(message = "community's id must not be empty or null")
        @Positive(message = "the community's id must be a positive number.")
        Integer communityId
) {}
