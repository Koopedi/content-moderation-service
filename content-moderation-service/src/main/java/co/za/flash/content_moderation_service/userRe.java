package co.za.flash.content_moderation_service;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRe extends JpaRepository<User, Long> {
}
