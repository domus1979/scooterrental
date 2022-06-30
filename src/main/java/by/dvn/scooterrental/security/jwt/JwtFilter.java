package by.dvn.scooterrental.security.jwt;

import by.dvn.scooterrental.security.SynteticUserDetails;
import by.dvn.scooterrental.security.SynteticUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;


@Component
public class JwtFilter extends GenericFilterBean {
    private static final String loginUrl = "/auth/login";

    private static final String registerurl = "/auth/register";

    private JwtProvider jwtProvider;

    private SynteticUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, SynteticUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws UsernameNotFoundException, ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getRequestURL().indexOf(loginUrl) < 0 &&
                httpServletRequest.getRequestURL().indexOf(registerurl) < 0) {
            String token = getTokenFromRequest(httpServletRequest);
            if (token != null && jwtProvider.validateToken(token)) {
                String login = jwtProvider.getLoginFromToken(token);
                    SynteticUserDetails userDetails = userDetailsService.loadUserByUsername(login);
                    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        String beginText = httpServletRequest.getHeader("Authorization");
        if (hasText(beginText) && beginText.startsWith("Bearer ")) {
            return beginText.substring(7);
        }
        return null;
    }

}
