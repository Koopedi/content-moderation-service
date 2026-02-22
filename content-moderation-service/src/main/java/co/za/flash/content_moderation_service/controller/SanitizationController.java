package co.za.flash.content_moderation_service.controller;

import co.za.flash.content_moderation_service.dto.SanitizeRequest;
import co.za.flash.content_moderation_service.service.SanitizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sanitize")
@RequiredArgsConstructor
@Tag(name = "Sanitization API", description = "API for sanitizing text using sensitive words list")
public class SanitizationController {

    private final SanitizationService sanitizationService;

    @Operation(
            summary = "Sanitize input text",
            description = "Replaces sensitive words in the input text with masked values"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Text sanitized successfully",
                    content = @Content(
                            mediaType = "text/plain",
                            schema = @Schema(example = "This is a ****** example")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body"
            )
    })
    @PostMapping
    public ResponseEntity<String> sanitize(
            @Valid @RequestBody SanitizeRequest request) {

        String sanitized = sanitizationService.sanitize(request.getText());
        return ResponseEntity.ok(sanitized);
    }

}
