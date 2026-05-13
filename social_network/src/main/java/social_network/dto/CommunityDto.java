package social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CommunityDto(

        Integer id,

        @Size(max = 50, message = "community name cannot be longer than 50 characters")
        @NotBlank(message = "name cannot be empty or null")
        String name,

        String description,

        @Positive(message = "the creator's di must be a positive number.")
        Integer creatorId,

        Set<UserDto> users,

        Set<PostInCommunityDto> posts
) {}
