package org.michalbrzezinski.securitate.feature;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String mainController() {
        return "secured endpoint";
    }

    @GetMapping(path = "public")
    public String publicEndpoint() {
        return "hello";
    }

}
