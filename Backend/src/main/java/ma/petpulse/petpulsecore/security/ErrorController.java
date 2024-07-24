package ma.petpulse.petpulsecore.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController {

    @GetMapping("/notAuthorized")
    @ResponseBody
    public String notAuthorized() {
        return "Access denied";
    }
}