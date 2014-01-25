package spring.xml.converter.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import spring.xml.converter.EntityTransformer;
import spring.xml.converter.XmlConverter;

import org.springframework.beans.factory.InitializingBean;

public class XmlConverterImpl implements XmlConverter, InitializingBean {

    private String rootName = "root";
    private DocumentBuilderFactory documentBuilderFactory;
    private Set<EntityTransformer> entityTransformer = new HashSet<EntityTransformer>();

    public Set<EntityTransformer> getEntityTransformer() {
        return entityTransformer;
    }

    public void setEntityTransformer(Set<EntityTransformer> entityTransformer) {
        this.entityTransformer = entityTransformer;
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return documentBuilderFactory;
    }

    public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
        this.documentBuilderFactory = documentBuilderFactory;
    }

    public final String getRootName() {
        return rootName;
    }

    public final void setRootName(String rootName) {
        this.rootName = rootName;
    }

    @Override
    public Source transform(Collection<Object> collection) {
        return this.transform(collection.toArray());
    }

    @Override
    public Source transform(Object... objects) {
        try {
            Document document = documentBuilderFactory.newDocumentBuilder().newDocument();
            Element root = document.createElement(rootName);

            for (Object object : objects) {
                if (object == null) {
                    continue;
                }

                for (EntityTransformer transformer : entityTransformer) {

                    if (transformer.supports(object.getClass())) {
                        transformer.transform(document, root, object);
                    }
                }
            }
            return new DOMSource(root);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (documentBuilderFactory == null) {
            throw new IllegalArgumentException("Property 'documentBuilderFactory' is required");
        }
    }
}
