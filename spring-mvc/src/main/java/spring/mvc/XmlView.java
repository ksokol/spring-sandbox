package spring.mvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.view.AbstractView;
import spring.xml.converter.XmlConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class XmlView extends AbstractView implements InitializingBean {

    private XmlConverter xmlConverter;

    private String url;

    private Set<String> viewsToBound;

    private String encoding = "UTF-8";

    private String doctype;

    private boolean indent = true;

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

    public final Set<String> getViewsToBound() {
        return viewsToBound;
    }

    public final void setViewsToBound(Set<String> viewsToBound) {
        this.viewsToBound = viewsToBound;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public final XmlConverter getXmlConverter() {
        return xmlConverter;
    }

    public final void setXmlConverter(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Source s = xmlConverter.transform(model.values());
        StreamResult streamResult = new StreamResult(response.getOutputStream());
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, encoding);

        if (doctype != null) {
            serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype);
        }
        if (indent) {
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        }

        response.setContentType(getContentType());
        serializer.transform(s, streamResult);
    }

    public boolean checkResource(Locale locale) {
        return viewsToBound.contains(getUrl());
    }

    public void afterPropertiesSet() throws Exception {
        if (getUrl() == null) {
            throw new IllegalArgumentException("Property 'url' is required");
        }

        if (getXmlConverter() == null) {
            throw new IllegalArgumentException("Property 'xmlConverter' is required");
        }

        if (getViewsToBound() == null) {
            throw new IllegalArgumentException("Property 'viewsToBound' is required");
        }
    }
}
