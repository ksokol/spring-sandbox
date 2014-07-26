package security;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * org.springframework.security.web.authentication.switchuser.SwitchUserFilter
 *
 * @author Mark St.Godard
 * @author Kamill Sokol
 */
public class CustomSwitchUserFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {

    public static final String SPRING_SECURITY_SWITCH_USERNAME_KEY = "j_switch_username";

    private ApplicationEventPublisher eventPublisher;
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private String switchFailureUrl;
    private String usernameParameter = SPRING_SECURITY_SWITCH_USERNAME_KEY;
    private UserDetailsService userDetailsService;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private AuthenticationFailureHandler failureHandler;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(userDetailsService, "userDetailsService must be specified");
        if (failureHandler == null) {
            failureHandler = switchFailureUrl == null ? new SimpleUrlAuthenticationFailureHandler() :
                    new SimpleUrlAuthenticationFailureHandler(switchFailureUrl);
        } else {
            Assert.isNull(switchFailureUrl, "You cannot set both a switchFailureUrl and a failureHandler");
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // check for switch or exit request
        if (requiresSwitchUser(request)) {
            // if set, attempt switch and store original
            try {
                Authentication targetUser = attemptSwitchUser(request);

                // update the current context to the new target user
                SecurityContextHolder.getContext().setAuthentication(targetUser);
            } catch (AuthenticationException e) {
                logger.debug("Switch User failed", e);
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    protected Authentication attemptSwitchUser(HttpServletRequest request) throws AuthenticationException {
        UsernamePasswordAuthenticationToken targetUserRequest;

        String username = request.getParameter(usernameParameter);

        if (username == null) {
            username = "";
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Attempt to switch to user [" + username + "]");
        }

        UserDetails targetUser = userDetailsService.loadUserByUsername(username);
        userDetailsChecker.check(targetUser);

        checkSwitchAllowed(targetUser);

        // OK, create the switch user token
        targetUserRequest = createSwitchUserToken(request, targetUser);

        if (logger.isDebugEnabled()) {
            logger.debug("Switch User Token [" + targetUserRequest + "]");
        }

        // publish event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new AuthenticationSwitchUserEvent(SecurityContextHolder.getContext().getAuthentication(), targetUser));
        }

        return targetUserRequest;
    }

    private void checkSwitchAllowed(UserDetails targetUser) {
        CustomUserDetails details = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String targetUsername = targetUser.getUsername();

        //target username has to be in linked accounts otherwise this is an unauthorized switch
        if(!details.getLinkedAccounts().contains(targetUsername)) {
            throw new InsufficientAuthenticationException("user switch not allowed");
        }
    }

    private UsernamePasswordAuthenticationToken createSwitchUserToken(HttpServletRequest request, UserDetails targetUser) {
        UsernamePasswordAuthenticationToken targetUserRequest;

        // get the original authorities
        Collection<? extends GrantedAuthority> orig = targetUser.getAuthorities();

        // add the new switch user authority
        List<GrantedAuthority> newAuths = new ArrayList<GrantedAuthority>(orig);

        // create the new authentication token
        targetUserRequest = new UsernamePasswordAuthenticationToken(targetUser, targetUser.getPassword(), newAuths);

        // set details
        targetUserRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return targetUserRequest;
    }

    protected boolean requiresSwitchUser(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap.containsKey(usernameParameter);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher)
            throws BeansException {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setSwitchFailureUrl(String switchFailureUrl) {
        Assert.isTrue(StringUtils.hasText(usernameParameter) && UrlUtils.isValidRedirectUrl(switchFailureUrl),
                "switchFailureUrl cannot be empty and must be a valid redirect URL");
        this.switchFailureUrl = switchFailureUrl;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        this.userDetailsChecker = userDetailsChecker;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

}

