package co.za.flash.content_moderation_service.controller;

import co.za.flash.content_moderation_service.dto.CreateWordRequest;
import co.za.flash.content_moderation_service.dto.UpdateWordRequest;
import co.za.flash.content_moderation_service.dto.WordResponse;
import co.za.flash.content_moderation_service.service.SensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
@Tag(name = "Sensitive Words API", description = "CRUD operations for managing sensitive words")
public class SensitiveWordController {

    private final SensitiveWordService sensitiveWordService;

    @Operation(summary = "Create a new sensitive word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Word created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<WordResponse> create(
            @Valid @RequestBody CreateWordRequest request) {

        WordResponse response = sensitiveWordService.create(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all sensitive words")
    @ApiResponse(responseCode = "200", description = "List of sensitive words returned")
    @GetMapping
    public ResponseEntity<List<WordResponse>> getAll() {
        return ResponseEntity.ok(sensitiveWordService.getAll());
    }

    @Operation(summary = "Get sensitive word by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Word found"),
            @ApiResponse(responseCode = "404", description = "Word not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WordResponse> getById(
            @Parameter(description = "ID of the word", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(sensitiveWordService.getById(id));
    }

    @Operation(summary = "Update a sensitive word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Word updated successfully"),
            @ApiResponse(responseCode = "404", description = "Word not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WordResponse> update(
            @Parameter(description = "ID of the word to update", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateWordRequest request) {


        System.out.println(request);

var dddiii = sensitiveWordService.update(id, request);
        return ResponseEntity.ok(dddiii);
    }

    @Operation(summary = "Delete a sensitive word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Word deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Word not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the word to delete", example = "1")
            @PathVariable Long id) {

        sensitiveWordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
