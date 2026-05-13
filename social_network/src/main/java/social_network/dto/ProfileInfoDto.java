package social_network.dto;

import social_network.enums.Gender;

import java.time.LocalDate;

public record ProfileInfoDto(
        Integer id,
        String name,
        String surname,
        Integer age,
        LocalDate birthday,
        String status,
        Gender gender
) {}
