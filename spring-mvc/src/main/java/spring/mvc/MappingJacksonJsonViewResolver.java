package spring.mvc;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class MappingJacksonJsonViewResolver extends AbstractCachingViewResolver implements Ordered {

    private Class<?> viewClass;

    private String contentType;

    private int order = Integer.MAX_VALUE;

    private final Map<String, Object> staticAttributes = new HashMap<String, Object>();

    public MappingJacksonJsonViewResolver() {
        setViewClass(MappingJacksonJsonView.class);
    }

    public final Map<String, Object> getAttributesMap() {
        return staticAttributes;
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
        MappingJacksonJsonView view = buildView(viewName);
        View result = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
        return result;
    }

    protected MappingJacksonJsonView buildView(String viewName) throws Exception {
        MappingJacksonJsonView view = (MappingJacksonJsonView) BeanUtils.instantiateClass(getViewClass());
        view.setContentType(getContentType());
        view.setAttributesMap(getAttributesMap());
        return view;
    }

    public int getOrder() {
        return this.order;
    }
}
