package security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Kamill Sokol
 */
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public CustomUsernamePasswordAuthenticationToken(final Object principal, final Object credentials) {
        super(principal, credentials);
    }

    public CustomUsernamePasswordAuthenticationToken(final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
