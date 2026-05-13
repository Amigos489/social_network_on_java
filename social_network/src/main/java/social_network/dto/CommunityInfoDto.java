package social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CommunityInfoDto(

        @NotBlank(message = "name cannot be empty or null.")
        String name,

        String description
) {}
