package co.za.flash.content_moderation_service.repository;

import co.za.flash.content_moderation_service.entity.SensitiveWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensitiveWordRepository extends JpaRepository<SensitiveWord, Long> {

    boolean existsByWord(String word);

}
