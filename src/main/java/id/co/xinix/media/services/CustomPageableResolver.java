package id.co.xinix.media.services;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

@Component("mediaCustomPageableResolver")
public class CustomPageableResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(CustomPageableResolver.class);

    private static final Map<String, Sort.Direction> DIRECTION_MAP = Map.of(
        "1",
        Sort.Direction.ASC,
        "-1",
        Sort.Direction.DESC,
        "desc",
        Sort.Direction.DESC,
        "asc",
        Sort.Direction.ASC
    );

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String skipParam = webRequest.getParameter("!skip");
        String limitParam = webRequest.getParameter("!limit");

        int skip = skipParam != null ? Integer.parseInt(skipParam) : 0;
        int limit = limitParam != null ? Integer.parseInt(limitParam) : 10;
        limit = Math.max(1, Math.min(limit, 1000));

        int page = skip / limit;

        Sort sort = Sort.unsorted();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramKey = entry.getKey();
            if (paramKey.startsWith("!sort[")) {
                String field = paramKey.substring(6, paramKey.length() - 1);
                String directionParam = entry.getValue()[0];
                Sort.Direction direction = DIRECTION_MAP.getOrDefault(directionParam.toLowerCase(), Sort.Direction.ASC);
                sort = Sort.by(new Sort.Order(direction, field));
            }
        }

        return PageRequest.of(page, limit, sort);
    }
}
