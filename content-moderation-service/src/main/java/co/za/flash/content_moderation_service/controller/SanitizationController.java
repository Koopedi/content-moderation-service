package co.za.flash.content_moderation_service.controller;

import co.za.flash.content_moderation_service.dto.SanitizeRequest;
import co.za.flash.content_moderation_service.service.SanitizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sanitize")
@RequiredArgsConstructor
public class SanitizationController {

    private final SanitizationService sanitizationService;

    @PostMapping
    public ResponseEntity<String> sanitize(
            @Valid @RequestBody SanitizeRequest request) {

        String sanitized = sanitizationService.sanitize(request.getText());
        return ResponseEntity.ok(sanitized);
    }
}
