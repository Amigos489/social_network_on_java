package social_network.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PostInCommunityDto (

        Integer id,

        String content,

        String authorName,

        String authorSurname,

        @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
        LocalDateTime dateTimePublication
) {}
