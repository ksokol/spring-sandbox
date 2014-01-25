package spring.xml.converter;

import java.util.Collection;

import javax.xml.transform.Source;

public interface XmlConverter {

    public Source transform(Collection<Object> collection);

    public Source transform(Object... object);

}