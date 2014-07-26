package security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Set<String> linkedAccounts(int i) {
        Set<String> linkedAccounts = new HashSet<String>();
        if(i+1 < users.length) {
            linkedAccounts.add(users[i+1]);
        }
        return linkedAccounts;
    }

}
