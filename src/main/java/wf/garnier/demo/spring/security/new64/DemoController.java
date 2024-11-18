package wf.garnier.demo.spring.security.new64;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

    @GetMapping("/")
    public String index(Authentication authentication) {
        return """
                <h1>Hello, %s!</h1>
                <p>
                <a href="/webauthn/register">Register passkeys</a>
                </p>
                <p>
                <a href="/logout">Logout</a>
                </p>
                """.formatted(authentication.getName());
    }
}
