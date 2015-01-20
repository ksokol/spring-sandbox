package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.GroupMembersDAO;
import entity.GroupMembers;

/**
 * @author we-are-borg
 */
@Transactional
@Service("GroupLoginServiceImpl")
public class GroupLoginServiceImpl implements UserDetailsService {

    @Autowired
    private GroupMembersDAO groupMembersDAO;

    @Autowired
    private GroupAssembler groupAssembler;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        GroupMembers group = groupMembersDAO.findPersonByUsername(username.toLowerCase());
        return groupAssembler.buildUserFromUserEntity(group);
    }
}
