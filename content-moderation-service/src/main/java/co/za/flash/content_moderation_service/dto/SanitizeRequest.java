package co.za.flash.content_moderation_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SanitizeRequest {

    @NotBlank(message = "Input text cannot be blank")
    private String text;
}
