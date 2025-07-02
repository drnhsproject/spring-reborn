package id.co.xinix.spring.framework;

import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class DefaultProfileUtil {
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap();
        defProperties.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defProperties);
    }
}
