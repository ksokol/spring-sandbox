package security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Kamill Sokol
 */
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, CustomUserDetails> userDetails = new HashMap<String, CustomUserDetails>(10);
    private final String[] users = {"example1","example2","example3"};

    public CustomUserDetailsService() {
        for (int i = 0; i < users.length; i++) {
            String username = users[i];
            Set<String> linkedAccounts = linkedAccounts(i);
            CustomUserDetails user = new CustomUserDetails(username, username, linkedAccounts, AuthorityUtils.createAuthorityList("USER_ROLE"));
            userDetails.put(username, user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userDetails.get(username));
    }

    public List<String> getAllUsernames() {
        return Arrays.asList(users);
    }

    public List<CustomUserDetails> getAll() {
        return new ArrayList<CustomUserDetails>(userDetails.values());
    }

    private Set<String> linkedAccounts(int i) {
        Set<String> linkedAccounts = new HashSet<String>();
        if(i+1 < users.length) {
            linkedAccounts.add(users[i+1]);
        }
        return linkedAccounts;
    }

    public void disableUser(String name) {
        CustomUserDetails c = userDetails.get(name);

        if(c == null) {
            return;
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(c.getUsername(), c.getPassword(), c.getLinkedAccounts(), false,
                c.isAccountNonExpired(), c.isCredentialsNonExpired(), c.isAccountNonLocked(), c.getAuthorities());

        userDetails.put(name, customUserDetails);
    }

}
