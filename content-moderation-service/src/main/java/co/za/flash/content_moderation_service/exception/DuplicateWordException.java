package co.za.flash.content_moderation_service.exception;

public class DuplicateWordException extends RuntimeException {

    public DuplicateWordException(String message) {
        super(message);
    }
}
