package dao;

import entity.Person;
import org.springframework.stereotype.Repository;

/**
 * @author Kamill Sokol
 */
@Repository
public class PersonDAO extends CrudDao<Person> {

    public Person findPersonByUsername(final String username) {
        return em.createQuery("select p from Person p where p.username = :username", Person.class).setParameter("username", username).getSingleResult();
    }
}
