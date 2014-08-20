package datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author Kamill Sokol
 */
public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Set<String> authorities = getAuthoritiesOfCurrentUser();
        if(authorities.contains("ROLE_TENANT1")) {
            return "TENANT1";
        }
        return "TENANT2";
    }

    private Set<String> getAuthoritiesOfCurrentUser() {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            return Collections.emptySet();
        }
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return AuthorityUtils.authorityListToSet(authorities);
    }
}
