package co.za.flash.content_moderation_service.service.impl;

import co.za.flash.content_moderation_service.dto.CreateWordRequest;
import co.za.flash.content_moderation_service.dto.UpdateWordRequest;
import co.za.flash.content_moderation_service.dto.WordResponse;
import co.za.flash.content_moderation_service.entity.SensitiveWord;
import co.za.flash.content_moderation_service.exception.DuplicateWordException;
import co.za.flash.content_moderation_service.exception.ResourceNotFoundException;
import co.za.flash.content_moderation_service.repository.SensitiveWordRepository;
import co.za.flash.content_moderation_service.service.SensitiveWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SensitiveWordServiceImpl implements SensitiveWordService {
    private final SensitiveWordRepository repository;

    @Override
    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public WordResponse create(CreateWordRequest request) {

        String normalized = request.getWord().toLowerCase().trim();

        if (repository.existsByWord(normalized)) {
          //  throw new DuplicateWordException("Word already exists");

            return WordResponse.builder()
                    .id(null)
                    .word(normalized)
                    .message("Word already exists")
                    .build();

        }

        SensitiveWord word = SensitiveWord.builder()
                .word(normalized)
                .build();

        return mapToResponseOnSave(repository.save(word), "Word successfully");

      //  return mapToResponse(repository.save(word));
    }

    @Override
    public List<WordResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WordResponse getById(Long id) {
        SensitiveWord word = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found"));
        return mapToResponse(word);
    }

    @Override
    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public WordResponse update(Long id, UpdateWordRequest request) {

        SensitiveWord existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Word not found"));

        String normalized = request.getWord().toLowerCase().trim();

        if (!existing.getWord().equals(normalized)
                && repository.existsByWord(normalized)) {
          //  throw new DuplicateWordException("Word already exists");
            return mapToResponseOnSave(existing, "Word already exists");
        }

        existing.setWord(normalized);

        return mapToResponseOnSave(repository.save(existing), "Word successfully");
    }

    @Override
    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Word not found");
        }

        repository.deleteById(id);
    }

    @Override
   // @Cacheable("sensitiveWords")
    public List<String> getAllWordsForSanitization() {

        return repository.findAll()
                .stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
    }

    private WordResponse mapToResponse(SensitiveWord word) {

        return WordResponse.builder()
                .id(word.getId())
                .word(word.getWord())
       //         .createdAt(word.getCreatedAt())
      //          .updatedAt(word.getUpdatedAt())
                .build();
    }

    private WordResponse mapToResponseOnSave(SensitiveWord word, String message) {

        return WordResponse.builder()
                .id(word.getId())
                .word(word.getWord())
                .message(message)
                //         .createdAt(word.getCreatedAt())
                //          .updatedAt(word.getUpdatedAt())
                .build();
    }

}
