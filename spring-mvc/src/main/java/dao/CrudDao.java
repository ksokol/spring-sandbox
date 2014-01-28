package dao;

import entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
class CrudDao<T extends AbstractEntity> {

    @PersistenceContext
    protected transient EntityManager em;

    private transient final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    CrudDao() {
        entityClass = (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T create(final T entity) {
       em.persist(entity);

       return entity;
    }

    public T findOne(final long id) {
        return em.find(entityClass, id);
    }

    public List<T> findAllById(final List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);
        Expression<String> exp = root.get("id");
        Predicate predicate = exp.in(idList);
        criteria.where(predicate);

        return em.createQuery(criteria).getResultList();
    }

    public T save(final T entity) {
        return em.merge(entity);
    }

    public void remove(final T entity) {
        em.remove(this.save(entity));
    }

    public List<T> findAll() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);

        return em.createQuery(criteria).getResultList();
    }

    public T findByProperty(String propertyName, Object value) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);
        Predicate condition = builder.equal(root.get(propertyName), value);
        criteria.where(condition);
        T result = em.createQuery(criteria).getSingleResult();

        return result;
    }

    public List<T> findAllByProperty(String propertyName, Object value) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);
        Predicate condition = builder.equal(root.get(propertyName), value);
        criteria.where(condition);
        List<T> list = em.createQuery(criteria).getResultList();

        return list;
    }

    void setEm(EntityManager em) {
        this.em = em;
    }

}
