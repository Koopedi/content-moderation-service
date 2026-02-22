package co.za.flash.content_moderation_service.service.impl;


import co.za.flash.content_moderation_service.dto.CreateWordRequest;
import co.za.flash.content_moderation_service.dto.UpdateWordRequest;
import co.za.flash.content_moderation_service.dto.WordResponse;
import co.za.flash.content_moderation_service.entity.SensitiveWord;
import co.za.flash.content_moderation_service.exception.ResourceNotFoundException;
import co.za.flash.content_moderation_service.repository.SensitiveWordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensitiveWordServiceImplTest {

    @Mock
    private SensitiveWordRepository repository;

    @InjectMocks
    private SensitiveWordServiceImpl service;

    private SensitiveWord word;

    @BeforeEach
    void setUp() {
        word = SensitiveWord.builder()
                .id(1L)
                .word("BAD")
                .build();
    }

    @Test
    void create_shouldReturnSuccess_whenWordDoesNotExist() {

        CreateWordRequest request = new CreateWordRequest("bad");
        when(repository.existsByWord("BAD")).thenReturn(false);
        when(repository.save(any())).thenReturn(word);

        WordResponse response = service.create(request);

        assertEquals("BAD", response.getWord());
        assertEquals("Word successfully created", response.getMessage());
    }

    @Test
    void create_shouldReturnDuplicateMessage_whenWordExists() {

        CreateWordRequest request = new CreateWordRequest("bad");
        when(repository.existsByWord("BAD")).thenReturn(true);

        WordResponse response = service.create(request);

        assertEquals("Word already exists", response.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void getById_shouldReturnWord_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(word));

        WordResponse response = service.getById(1L);

        assertEquals("BAD", response.getWord());
    }

    @Test
    void getAll_shouldReturnListOfWordResponses() {

        SensitiveWord word1 = SensitiveWord.builder()
                .id(1L)
                .word("BAD")
                .build();

        SensitiveWord word2 = SensitiveWord.builder()
                .id(2L)
                .word("UGLY")
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(word1, word2));

        List<WordResponse> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("BAD", result.get(0).getWord());
        assertEquals("UGLY", result.get(1).getWord());

        verify(repository).findAll();
    }

    @Test
    void update_shouldReturnSuccess_whenWordIsValid() {

        SensitiveWord existing = SensitiveWord.builder()
                .id(1L)
                .word("BAD")
                .build();

        UpdateWordRequest request = new UpdateWordRequest("good");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByWord("GOOD")).thenReturn(false);
        when(repository.save(any())).thenReturn(existing);

        WordResponse response = service.update(1L, request);

        assertEquals("GOOD", response.getWord());
        assertEquals("Word successfully updated", response.getMessage());

        verify(repository).save(existing);
    }

    @Test
    void update_shouldReturnDuplicateMessage_whenWordAlreadyExists() {

        SensitiveWord existing = SensitiveWord.builder()
                .id(1L)
                .word("BAD")
                .build();

        UpdateWordRequest request = new UpdateWordRequest("OTHER");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.existsByWord("OTHER")).thenReturn(true);

        WordResponse response = service.update(1L, request);

        assertEquals("Word already exists", response.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void update_shouldThrowException_whenWordNotFound() {

        UpdateWordRequest request = new UpdateWordRequest("good");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(1L, request));

        verify(repository, never()).save(any());
    }
}
