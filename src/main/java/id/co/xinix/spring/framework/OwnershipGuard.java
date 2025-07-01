package id.co.xinix.spring.framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Component("ownershipGuard")
@Getter
@AllArgsConstructor
public class OwnershipGuard {

    private final ApplicationContext applicationContext;
    private final JwtDecoder jwtDecoder;

    public boolean isOwner(String entityName, Long resourceId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        JpaRepository<?, Long> repository = getRepositoryForEntity(entityName);
        Optional<?> entityOpt = repository.findById(resourceId);

        if (entityOpt.isEmpty()) {
            throw new AccessDeniedException("Resource with ID " + resourceId + " not found for entity " + entityName);
        }

        Object entity = entityOpt.get();

        for (String methodName : List.of("getCreatedBy", "getOwnerId", "getUserId")) {
            try {
                Method method = entity.getClass().getMethod(methodName);
                Object ownerValue = method.invoke(entity);

                if (ownerValue == null) {
                    throw new AccessDeniedException("Ownership information not found on entity");
                }

                if (principal instanceof JwtUserPrincipal(Long userId, String username)) {

                    if (ownerValue instanceof Long ownerId) {
                        return ownerId.equals(userId);
                    }

                    if (ownerValue instanceof String ownerUsername) {
                        return ownerUsername.equals(username);
                    }
                }

            } catch (NoSuchMethodException ignored) {
                // Try next method
            } catch (Exception e) {
                throw new IllegalStateException("Error accessing ownership method on entity", e);
            }
        }

        throw new IllegalStateException("Entity must have a method like getCreatedBy/getOwnerId/getUserId");
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<?, Long> getRepositoryForEntity(String entityName) {
        String repoBeanName = entityName + "Repository";
        repoBeanName = Introspector.decapitalize(repoBeanName);
        if (!applicationContext.containsBean(repoBeanName)) {
            throw new IllegalArgumentException("Repository not found for entity: " + entityName);
        }
        return (JpaRepository<?, Long>) applicationContext.getBean(repoBeanName);
    }
}