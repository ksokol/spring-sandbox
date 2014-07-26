package security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * @author Kamill Sokol
 */
public class CustomUserDetails extends User {

    private Set<String> linkedAccounts;

    public CustomUserDetails(String username, String password, Set<String> linkedAccounts, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.linkedAccounts = linkedAccounts;
    }

    public CustomUserDetails(CustomUserDetails user) {
        this(user.getUsername(), user.getPassword(), user.getLinkedAccounts(), user.getAuthorities());
    }

    public Set<String> getLinkedAccounts() {
        return linkedAccounts;
    }
}
