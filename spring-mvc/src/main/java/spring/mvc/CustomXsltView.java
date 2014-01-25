package spring.mvc;

import java.util.Locale;
import java.util.Map;

import javax.xml.transform.Source;

import spring.xml.converter.XmlConverter;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.xslt.XsltView;

public final class CustomXsltView extends XsltView {

    @Override
    public boolean isContextRequired() {
        return true;
    }

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        ApplicationContext ac = getApplicationContext();
        Resource r = ac.getResource(getUrl());
        return r.exists();
    }

    @Override
    protected Source locateSource(Map<String, Object> model) throws Exception {
        XmlConverter converter = (XmlConverter) getApplicationContext().getBean("xmlConverter");
        return converter.transform(model.values());
    }

    @Override
    protected Source convertSource(Object source) throws Exception {
        throw new UnsupportedOperationException();
    }
}
