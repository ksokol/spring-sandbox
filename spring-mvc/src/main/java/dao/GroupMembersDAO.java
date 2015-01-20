package dao;

import org.springframework.stereotype.Repository;

import entity.GroupMembers;

/**
 * @author Kamill Sokol
 */
@Repository
public class GroupMembersDAO extends CrudDao<GroupMembers> {

    public GroupMembers findPersonByUsername(final String groupId) {
        return em.createQuery("select g from GroupMembers g where g.username = :username", GroupMembers.class).setParameter("username", groupId).getSingleResult();
    }
}
