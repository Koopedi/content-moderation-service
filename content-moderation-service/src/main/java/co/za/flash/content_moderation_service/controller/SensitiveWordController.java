package co.za.flash.content_moderation_service.controller;

import co.za.flash.content_moderation_service.dto.CreateWordRequest;
import co.za.flash.content_moderation_service.dto.UpdateWordRequest;
import co.za.flash.content_moderation_service.dto.WordResponse;
import co.za.flash.content_moderation_service.service.SensitiveWordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
public class SensitiveWordController {

    private final SensitiveWordService sensitiveWordService;

    @PostMapping
    public ResponseEntity<WordResponse> create(
            @Valid @RequestBody CreateWordRequest request) {

        WordResponse response = sensitiveWordService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WordResponse>> getAll() {
        return ResponseEntity.ok(sensitiveWordService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sensitiveWordService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WordResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWordRequest request) {

        return ResponseEntity.ok(sensitiveWordService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensitiveWordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
