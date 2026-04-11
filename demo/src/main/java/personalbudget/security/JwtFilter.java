package personalbudget.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /** Paths that must accept requests without valid JWT (same idea as SecurityConfig permitAll). */
    private static final String[] PUBLIC_PATHS = {
            "/",
            "/api/support",
            "/api/app_users/login",
            "/api/app_users/register",
            "/api/app_users/forgot-password",
            "/api/app_users/reset-password",
            "/api/app_users/support"
    };

    private static boolean isPublicPath(String path) {
        for (String pattern : PUBLIC_PATHS) {
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    private static String pathWithinApplication(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (contextPath != null && !contextPath.isEmpty() && uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }
        return uri.isEmpty() ? "/" : uri;
    }
	
	
	  @Autowired
	    private JwtUtil jwtUtil;

	  @Autowired
	    private AppUserDetailsService appUserDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain)
	            throws ServletException, IOException {

	        // CORS preflight never carries Authorization; let CorsFilter + Security handle it.
	        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        if (isPublicPath(pathWithinApplication(request))) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        String authHeader = request.getHeader("Authorization");

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {

	            String token = authHeader.substring(7);

	            if (!jwtUtil.validateToken(token)) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }

	            try {
	                String email = jwtUtil.extractEmail(token);
	                UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
	                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities());
	                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(auth);
	            } catch (Exception e) {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                return;
	            }
	        }

	        filterChain.doFilter(request, response);
	    }

}
