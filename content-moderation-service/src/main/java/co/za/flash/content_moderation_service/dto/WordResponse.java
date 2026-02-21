package co.za.flash.content_moderation_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordResponse {
    private Long id;
    private String word;
    private String message;
}
