package id.co.xinix.auth.modules.role.application.dto;

import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
public class QueryFilter {

    private String search;

    private Integer status;

    public String buildWhereClause() {
        StringJoiner whereClause = new StringJoiner("", " WHERE is_active is true", "");

        if (search != null && !search.isEmpty()) {
            whereClause.add(" AND (code LIKE :search OR name LIKE :search)");
        }

        if (status != null) {
            whereClause.add(" AND status = :status");
        }

        return whereClause.toString();
    }

    public void applyBindingParameters(Query query) {
        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search + "%");
        }

        if (status != null) {
            query.setParameter("status", status);
        }
    }
}
