package social_network.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MessageDto(
        String text,
        String senderName,
        String senderSurname,
        @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
        LocalDateTime dateTimeSending
) {}
