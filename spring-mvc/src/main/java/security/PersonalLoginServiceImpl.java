package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.PersonDAO;
import entity.Person;

/**
 * @author we-are-borg
 */
@Transactional
@Service("PersonalLoginServiceImpl")
public class PersonalLoginServiceImpl implements UserDetailsService {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PersonalAssembler assembler;

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(final PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonalAssembler getAssembler() {
        return assembler;
    }

    public void setAssembler(final PersonalAssembler assembler) {
        this.assembler = assembler;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Person person = personDAO.findPersonByUsername(username.toLowerCase());
        return assembler.buildUserFromUserEntity(person);
    }
}
