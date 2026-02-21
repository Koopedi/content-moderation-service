package co.za.flash.content_moderation_service.service;

import co.za.flash.content_moderation_service.dto.CreateWordRequest;
import co.za.flash.content_moderation_service.dto.UpdateWordRequest;
import co.za.flash.content_moderation_service.dto.WordResponse;

import java.util.List;

public interface SensitiveWordService {
    WordResponse create(CreateWordRequest request);

    List<WordResponse> getAll();

    WordResponse getById(Long id);

    WordResponse update(Long id, UpdateWordRequest request);

    void delete(Long id);

    List<String> getAllWordsForSanitization();
}
