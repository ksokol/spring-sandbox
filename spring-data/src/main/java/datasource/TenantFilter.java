package datasource;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kamill Sokol
 */
public class TenantFilter implements Filter {

    private final Pattern pattern = Pattern.compile(";\\s*tenant\\s*=\\s*(\\w+)");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tenant = matchTenantSystemIDToken(httpRequest.getRequestURI());
        Tenant.setCurrentTenant(tenant);
        chain.doFilter(request, response);
        Tenant.clearCurrentTenant();
    }

    @Override
    public void destroy() {}

    private String matchTenantSystemIDToken(final String uri) {
        final Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
