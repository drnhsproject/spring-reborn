package id.co.xinix.auth.modules.role.application.dto;

import id.co.xinix.auth.services.DynamicQueryFilter;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryFilter {

    private String search;
    private Integer status;
    private DynamicQueryFilter dynamicQueryFilter;

    public QueryFilter(Map<String, String> parameterMap) {
        this.dynamicQueryFilter = new DynamicQueryFilter(parameterMap);

        if (parameterMap.containsKey("!search")) {
            this.search = parameterMap.get("!search");
        }

        if (parameterMap.containsKey("status")) {
            this.status = Integer.valueOf(parameterMap.get("status"));
        }
    }

    public String buildWhereClause() {
        StringJoiner whereClause = new StringJoiner("", " WHERE 1=1", "");

        if (search != null && !search.isEmpty()) {
            whereClause.add(" AND (code LIKE :search OR name LIKE :search)");
        }

        if (status != null) {
            whereClause.add(" AND status = :status");
        }

        if (dynamicQueryFilter != null) {
            whereClause.add(dynamicQueryFilter.buildWhereClause());
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

        if (dynamicQueryFilter != null) {
            dynamicQueryFilter.applyParams(query);
        }
    }
}
