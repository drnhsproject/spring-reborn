package id.co.xinix.spring.modules.sysparam.application.dto;

import id.co.xinix.spring.services.LikeOperatorResolver;
import id.co.xinix.spring.services.SqlQuoter;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueryFilter {

    private String search;
    private Integer status;
    private LikeOperatorResolver likeOperator;
    private SqlQuoter quoter;

    public String buildWhereClause() {
        StringJoiner whereClause = new StringJoiner("", " WHERE 1=1", "");

        if (search != null && !search.isEmpty()) {
            String like = likeOperator.get();
            whereClause.add(" AND (" +
                    quoter.quote("group") + " " + like + " :search OR " +
                    quoter.quote("key") + " " + like + " :search OR " +
                    quoter.quote("value") + " " + like + " :search OR " +
                    "long_value " + like + " :search)");
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
