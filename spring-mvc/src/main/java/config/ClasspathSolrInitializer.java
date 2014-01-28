package config;

import org.apache.solr.core.CoreContainer;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

class ClasspathSolrInitializer extends CoreContainer.Initializer {

    private static final String SOLR_XML = "solr/solr.xml";
    private static final Logger log = Logger.getLogger(ClasspathSolrInitializer.class.getName());

    @Override
    public CoreContainer initialize() throws IOException, ParserConfigurationException, SAXException {
        File home = new ClassPathResource(SOLR_XML).getFile();

        log.info("looking for cores in " + home.getParent());

        CoreContainer cores = new CoreContainer(home.getParent());
        cores.load(home.getParent(), home.getAbsoluteFile());
        containerConfigFilename = cores.getConfigFile().getName();

        return cores;
    }
}
