package personalbudget;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot 3 / Spring 6 no longer matches URLs with a trailing slash to the same
 * handler as without (e.g. {@code /login/} vs {@code /login}). Mobile clients and WebViews
 * often append a trailing slash, which would otherwise yield 404 or failed auth.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }
}

