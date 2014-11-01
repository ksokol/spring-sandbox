package security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

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
        this(user.getUsername(), user.getPassword(), user.getLinkedAccounts(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), user.getAuthorities());
    }

    public CustomUserDetails(String username, String password, Set<String> linkedAccounts, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.linkedAccounts = linkedAccounts;
    }

    public Set<String> getLinkedAccounts() {
        return linkedAccounts;
    }
}
