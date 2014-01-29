package config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;
import entity.listener.SolrUpdateListener;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
@Configuration
public class SearchConfig {

    @Autowired
    private EntityManagerFactory emf;

    private CoreContainer cores;

    public SearchConfig() throws ParserConfigurationException, SAXException, IOException {
        cores = new ClasspathSolrInitializer().initialize();
    }

    @Bean
    public CoreContainer coreContainer() throws ParserConfigurationException, SAXException, IOException {
        return cores;
    }

    @PostConstruct
    public void registerListeners() throws UnknownHostException {
        HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) emf;
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
        EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);

        SolrUpdateListener listener = listener();

        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(listener);
        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(listener);
        registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(listener);
    }

    private SolrServer solrServer() throws UnknownHostException {
        return new EmbeddedSolrServer(cores, "");
    }

    private SolrUpdateListener listener() throws UnknownHostException {
        return new SolrUpdateListener(solrServer());
    }
}
