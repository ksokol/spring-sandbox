package security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

/**
 * @author Kamill Sokol
 */
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Map<String, String> roleUrlMap;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(CollectionUtils.isEmpty(authentication.getAuthorities())) {
            throwException();
        }
        for (final GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String route = roleUrlMap.get(grantedAuthority.getAuthority());
            if(route== null) {
                continue;
            }
            response.sendRedirect(request.getContextPath() + route);
            return;
        }
        throwException();
    }

    public Map<String, String> getRoleUrlMap() {
        return roleUrlMap;
    }

    public void setRoleUrlMap(Map<String, String> roleUrlMap) {
        this.roleUrlMap = roleUrlMap;
    }

    private void throwException() {
        throw new RuntimeException("route not found");
    }
}