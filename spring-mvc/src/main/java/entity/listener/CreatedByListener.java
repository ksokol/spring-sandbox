package entity.listener;

import entity.AbstractEntity;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.event.spi.*;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 *
 *     http://anshuiitk.blogspot.de/2010/11/hibernate-pre-database-opertaion-event.html
 */
public class CreatedByListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        AbstractEntity entity = (AbstractEntity) event.getEntity();
        String creator = getCreator();
        String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
        Object[] state = event.getState();
        setValue(state, propertyNames, "createdBy", creator, entity);
        entity.setCreatedBy(creator);
        return false;
    }

    private String getCreator() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value, Object entity) {
        int index = ArrayUtils.indexOf(propertyNames, propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        } else {
            throw new RuntimeException("Field '" + propertyToSet + "' not found on entity '" + entity.getClass().getName() + "'.");
        }
    }

}
