package co.za.flash.content_moderation_service.util;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public final class RegexUtil {
    private RegexUtil() {
    }

    public static Pattern buildWordPattern(List<String> words) {

        if (words == null || words.isEmpty()) {
            return null;
        }

        String pattern = words.stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));

        return Pattern.compile("\\b(" + pattern + ")\\b", Pattern.CASE_INSENSITIVE);
    }
}
