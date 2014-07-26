package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import security.CustomUserDetails;
import security.CustomUserDetailsService;

import java.util.Map;

/**
 * @author Kamill Sokol
 */
@Controller
public class UserSwitchController {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserSwitchController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value="/switch.do", produces = "text/html")
    public String index(Map<String, Object> model, Authentication authentication) {
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();

        model.put("user", details);
        model.put("usernames", userDetailsService.getAllUsernames());

        return "switch";
    }
}
