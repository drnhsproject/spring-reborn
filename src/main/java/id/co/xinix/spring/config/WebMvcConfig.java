package id.co.xinix.spring.config;

import id.co.xinix.spring.services.CustomPageableResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final CustomPageableResolver customPageableResolver;

    public WebMvcConfig(CustomPageableResolver customPageableResolver) {
        this.customPageableResolver = customPageableResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableResolver);
    }
}
