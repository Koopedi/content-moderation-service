package co.za.flash.content_moderation_service.config;

import co.za.flash.content_moderation_service.entity.SensitiveWord;
import co.za.flash.content_moderation_service.repository.SensitiveWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final SensitiveWordRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        List<SensitiveWord> defaultWords = List.of(
                new SensitiveWord(null, "SELECT"),
                new SensitiveWord(null, "INSERT"),
                new SensitiveWord(null, "UPDATE"),
                new SensitiveWord(null, "DELETE"),
                new SensitiveWord(null, "DROP"),
                new SensitiveWord(null, "TRUNCATE"),
                new SensitiveWord(null, "ALTER"),
                new SensitiveWord(null, "CREATE")
        );

        repository.saveAll(defaultWords);

        System.out.println("Default sensitive words loaded into database.");
    }
}
