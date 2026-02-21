package co.za.flash.content_moderation_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateWordRequest {
    @NotBlank(message = "Word cannot be blank")
    @Size(max = 100, message = "Word cannot exceed 100 characters")
    private String word;
}
