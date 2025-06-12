package id.co.xinix.media.services;

import id.co.xinix.spring.exception.DomainException;
import id.co.xinix.spring.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service("mediaIdValidatorService")
public class IdValidationService {

    public <T, ID> void validateIdForUpdate(
            JpaRepository<T, ID> repository,
            ID pathId,
            ID bodyId,
            String entityName
    ) {
        if (bodyId == null) {
            throw new DomainException("for update id can't null");
        }

        if (!pathId.equals(bodyId)) {
            throw new DomainException("mismatched id for " + entityName);
        }

        if (!repository.existsById(pathId)) {
            throw new NotFoundException(entityName + " not found with id: " + pathId);
        }
    }

    public <ID> void validateNotNull(ID bodyId) {
        if (bodyId != null) {
            throw new DomainException("id already exists");
        }
    }
}
