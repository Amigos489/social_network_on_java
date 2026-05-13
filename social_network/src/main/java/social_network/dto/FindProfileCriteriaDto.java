package social_network.dto;

import jakarta.validation.constraints.Positive;
import social_network.enums.Gender;

public record FindProfileCriteriaDto(
        String name,
        String surname,
        @Positive(message = "age must be a positive number.")
        Integer minAge,
        @Positive(message = "age must be a positive number.")
        Integer maxAge,
        Gender gender
) {}
