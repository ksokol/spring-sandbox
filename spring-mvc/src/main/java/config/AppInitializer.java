package config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.register(AppConfig.class);

		servletContext.addListener(new ContextLoaderListener(root));

		AnnotationConfigWebApplicationContext mvc = new AnnotationConfigWebApplicationContext();
		mvc.register(MvcConfig.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("appServlet", new DispatcherServlet(mvc));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", characterEncodingFilter);
		encodingFilter.addMappingForServletNames(null, true, "/*");

        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", springSecurityFilterChain);
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
