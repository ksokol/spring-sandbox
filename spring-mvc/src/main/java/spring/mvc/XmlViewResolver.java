package spring.mvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import spring.xml.converter.XmlConverter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

public class XmlViewResolver extends AbstractCachingViewResolver implements Ordered {

    @Autowired
    private XmlConverter xmlConverter;

    private Class<?> viewClass;

    private String contentType;

    private String requestContextAttribute;

    private int order = Integer.MAX_VALUE;

    private Set<String> viewsToBound;

    private String encoding = "UTF-8";

    private String doctype;

    private boolean indent = true;

    public final XmlConverter getXmlConverter() {
        return xmlConverter;
    }

    public final void setXmlConverter(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }

    public final String getEncoding() {
        return encoding;
    }

    public final void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public final String getDoctype() {
        return doctype;
    }

    public final void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public final boolean isIndent() {
        return indent;
    }

    public final void setIndent(boolean indent) {
        this.indent = indent;
    }

    private final Map<String, Object> staticAttributes = new HashMap<String, Object>();

    public XmlViewResolver() {
        setViewClass(XmlView.class);
        setViewsToBound(new HashSet<String>());
    }

    public final Set<String> getViewsToBound() {
        return viewsToBound;
    }

    public final void setViewsToBound(Set<String> viewsToBound) {
        this.viewsToBound = viewsToBound;
    }

    public final Map<String, Object> getAttributesMap() {
        return staticAttributes;
    }

    public final String getRequestContextAttribute() {
        return requestContextAttribute;
    }

    public final void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    public Class<?> getViewClass() {
        return this.viewClass;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String getContentType() {
        return this.contentType;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        XmlView view = buildView(viewName);
        View result = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
        return (view.checkResource(locale) ? result : null);
    }

    protected XmlView buildView(String viewName) throws Exception {
        XmlView view = (XmlView) BeanUtils.instantiateClass(getViewClass());

        view.setUrl(viewName);
        view.setXmlConverter(getXmlConverter());
        view.setViewsToBound(getViewsToBound());
        view.setEncoding(getEncoding());
        view.setDoctype(getDoctype());
        view.setIndent(isIndent());
        view.setContentType(getContentType());
        view.setRequestContextAttribute(getRequestContextAttribute());
        view.setAttributesMap(getAttributesMap());

        return view;
    }

    public int getOrder() {
        return this.order;
    }
}
