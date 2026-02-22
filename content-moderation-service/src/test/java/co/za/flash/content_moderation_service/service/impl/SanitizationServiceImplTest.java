package co.za.flash.content_moderation_service.service.impl;

import co.za.flash.content_moderation_service.service.SensitiveWordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SanitizationServiceImplTest {

    @Mock
    private SensitiveWordService sensitiveWordService;

    @InjectMocks
    private SanitizationServiceImpl sanitizationService;

    @Test
    void sanitize_ShouldReturnInput_WhenNoSensitiveWordsExist() {

        when(sensitiveWordService.getAllWordsForSanitization())
                .thenReturn(Collections.emptyList());

        String input = "This is a normal sentence.";

        String result = sanitizationService.sanitize(input);

        assertEquals(input, result);
    }

    @Test
    void sanitize_ShouldReplaceSingleSensitiveWord() {

        when(sensitiveWordService.getAllWordsForSanitization())
                .thenReturn(Collections.singletonList("bad"));

        String input = "This is a bad example.";

        String result = sanitizationService.sanitize(input);

        assertEquals("This is a ****** example.", result);
    }

    @Test
    void sanitize_ShouldReplaceMultipleSensitiveWords() {

        when(sensitiveWordService.getAllWordsForSanitization())
                .thenReturn(Arrays.asList("bad", "ugly"));

        String input = "This is bad and ugly.";

        String result = sanitizationService.sanitize(input);

        assertEquals("This is ****** and ******.", result);
    }

    @Test
    void sanitize_ShouldBeCaseInsensitive() {

        when(sensitiveWordService.getAllWordsForSanitization())
                .thenReturn(Collections.singletonList("bad"));

        String input = "This is BAD and Bad and bad.";

        String result = sanitizationService.sanitize(input);

        assertEquals("This is ****** and ****** and ******.", result);
    }

    @Test
    void sanitize_ShouldMatchWholeWordsOnly() {

        when(sensitiveWordService.getAllWordsForSanitization())
                .thenReturn(Collections.singletonList("bad"));

        String input = "This is badly written.";

        String result = sanitizationService.sanitize(input);

        assertEquals("This is badly written.", result);
    }
}
