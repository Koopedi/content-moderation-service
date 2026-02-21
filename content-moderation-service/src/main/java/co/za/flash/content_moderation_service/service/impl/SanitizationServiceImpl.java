package co.za.flash.content_moderation_service.service.impl;

import co.za.flash.content_moderation_service.service.SanitizationService;
import co.za.flash.content_moderation_service.service.SensitiveWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanitizationServiceImpl implements SanitizationService {

    private final SensitiveWordService sensitiveWordService;

    @Override
    public String sanitize(String input) {

        List<String> words = sensitiveWordService.getAllWordsForSanitization();

        if (words.isEmpty()) {
            return input;
        }

        String pattern = words.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        return input.replaceAll("(?i)\\b(" + pattern + ")\\b", "******");
    }
}
