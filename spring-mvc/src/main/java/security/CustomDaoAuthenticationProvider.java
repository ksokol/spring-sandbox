package security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * @author Kamill Sokol
 */
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private final Class<?> clazz;

    public CustomDaoAuthenticationProvider(final String clazz) throws ClassNotFoundException {
        this.clazz = Class.forName(clazz);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return clazz.equals(authentication);
    }
}
