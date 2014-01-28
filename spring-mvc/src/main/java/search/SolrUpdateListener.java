package search;

import org.apache.solr.client.solrj.SolrServer;
import org.hibernate.event.spi.*;

import java.io.Serializable;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
public class SolrUpdateListener implements PostInsertEventListener,PostUpdateEventListener, PostDeleteEventListener {

    private SolrServer solrServer;

    public SolrUpdateListener(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        update(event.getEntity());
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        Serializable id = event.getId();
        try {
            solrServer.deleteById(id.toString());
            solrServer.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        update(event.getEntity());
    }

    private void update(Object object) {
        try {
            solrServer.addBean(object);
            solrServer.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
