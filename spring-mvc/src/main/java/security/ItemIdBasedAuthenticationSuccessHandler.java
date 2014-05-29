package security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kamill Sokol
 */
public class ItemIdBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final String defaultTargetUrl;

	public ItemIdBasedAuthenticationSuccessHandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
		String redirectUrl = request.getContextPath() + defaultTargetUrl;

		if(StringUtils.hasText(details.getItemId())) {
			//TODO sanity and security check for itemid needed
			redirectUrl = "item/" + details.getItemId();
		}

		response.sendRedirect(redirectUrl);
	}
}
