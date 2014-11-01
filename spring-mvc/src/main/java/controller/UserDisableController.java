package controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import security.CustomUserDetailsService;

/**
 * @author Kamill Sokol
 */
@Controller
public class UserDisableController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @RequestMapping(value = "/user/disable.do", method = RequestMethod.GET)
    public String get(Map<String, Object> model) {
        model.put("users", customUserDetailsService.getAll());
        return "user/disable";
    }

    @ResponseBody
    @RequestMapping(value = "/user/disable.do", method = RequestMethod.POST)
    public void post(@RequestBody DisableRequest request, HttpSession session) {
        customUserDetailsService.disableUser(request.getUsername());
    }

    public static class DisableRequest {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(final String username) {
            this.username = username;
        }
    }
}
