package security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.GroupMembers;

/**
 * @author we-are-borg
 */
@Service("GroupAssembler")
public class GroupAssembler {
    @Transactional(readOnly = true)
    User buildUserFromUserEntity(GroupMembers userEntity){
        String username = userEntity.getUsername().toLowerCase();
        String password = userEntity.getPassword();

        boolean enabled = userEntity.isEnabled();
        boolean accountNonExpired = userEntity.isAccountNonExpired();
        boolean credentialsNonExpired = userEntity.isCredentialsNonExpired();
        boolean accountNonLocked = userEntity.isAccountNonLocked();

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("GROUP_LOGIN"));

        User user = new User(username,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
        return  user;
    }
}
