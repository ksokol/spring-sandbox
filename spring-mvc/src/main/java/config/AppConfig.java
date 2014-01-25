package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;
import spring.mvc.CustomXsltView;
import spring.mvc.MappingJacksonJsonViewResolver;
import spring.mvc.XmlViewResolver;
import spring.xml.converter.EntityTransformer;
import spring.xml.converter.XmlConverter;
import spring.xml.converter.impl.ProductEntityTransformer;
import spring.xml.converter.impl.XmlConverterImpl;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"controller", "spring", "dao"})
@EnableTransactionManagement
@Import(PersistenceJPAConfig.class)

public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.do");
    }

    @Bean
    public XmlViewResolver xmlViewResolver() {
        XmlViewResolver xmlViewResolver = new XmlViewResolver();

        xmlViewResolver.setContentType(MediaType.APPLICATION_XML_VALUE);
        xmlViewResolver.setViewsToBound(new HashSet<String>(Arrays.asList("product")));

        return xmlViewResolver;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(XmlViewResolver xmlViewResolver) {
        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        HashMap<String, String> mediaTypes = new HashMap<String, String>();

        mediaTypes.put("html", MediaType.TEXT_HTML_VALUE);
        mediaTypes.put("xml", MediaType.APPLICATION_XML_VALUE);
        mediaTypes.put("json", MediaType.APPLICATION_JSON_VALUE);
        mediaTypes.put("do", MediaType.TEXT_HTML_VALUE);

        contentNegotiatingViewResolver.setMediaTypes(mediaTypes);
        contentNegotiatingViewResolver.setOrder(1);

        XsltViewResolver xsltViewResolver = new XsltViewResolver();
        xsltViewResolver.setViewClass(CustomXsltView.class);
        xsltViewResolver.setPrefix("/WEB-INF/xslt/");
        xsltViewResolver.setSuffix(".xsl");
        xsltViewResolver.setContentType(MediaType.TEXT_HTML_VALUE);
        xsltViewResolver.setCacheTemplates(false);

        MappingJacksonJsonViewResolver mappingJacksonJsonViewResolver = new MappingJacksonJsonViewResolver();
        mappingJacksonJsonViewResolver.setViewClass(MappingJacksonJsonView.class);
        mappingJacksonJsonViewResolver.setContentType(MediaType.APPLICATION_JSON_VALUE);

        contentNegotiatingViewResolver.setViewResolvers(Arrays.asList(new ViewResolver[]{xmlViewResolver, xsltViewResolver, mappingJacksonJsonViewResolver}));

        return contentNegotiatingViewResolver;
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setViewClass(JstlView.class);
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setOrder(2);
        internalResourceViewResolver.setContentType(MediaType.TEXT_HTML_VALUE);

        return internalResourceViewResolver;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DocumentBuilderFactory documentBuilderFactory() {
        return javax.xml.parsers.DocumentBuilderFactory.newInstance();
    }

    @Bean
    public XmlConverter xmlConverter() {
        XmlConverterImpl xmlConverter = new XmlConverterImpl();
        HashSet<EntityTransformer> entityTransformers = new HashSet<EntityTransformer>();

        entityTransformers.add(new ProductEntityTransformer());
        xmlConverter.setEntityTransformer(entityTransformers);
        xmlConverter.setDocumentBuilderFactory(documentBuilderFactory());

        return xmlConverter;
    }
}
