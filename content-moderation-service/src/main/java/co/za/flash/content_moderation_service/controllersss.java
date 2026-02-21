package co.za.flash.content_moderation_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class controllersss {

    @GetMapping
    public String ddd() {
        return "koopedi";
    }
}
