package social_network.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MessageToGroupChatDto(
        @Size(max = 200, message = "the message cannot be longer than 200 characters")
        @NotBlank(message = "the message cannot be empty or null.")
        String text,
        @Positive(message = "the chat id It must be positive.")
        @NotBlank(message = "the chat id cannot be empty or null.")
        Integer chatId
) {}
