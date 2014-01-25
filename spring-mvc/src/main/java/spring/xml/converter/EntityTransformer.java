package spring.xml.converter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface EntityTransformer {

    public abstract boolean supports(Class<?> clazz);

    public abstract void transform(Document document, Element root, Object object);

}