package id.co.xinix.auth.services;

import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamicQueryFilter {

    public record Condition(String field, String operator, String value) {}

    private final List<Condition> conditions = new ArrayList<>();

    public DynamicQueryFilter(Map<String, String> parameterMap) {
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            String rawKey = entry.getKey();
            String value = entry.getValue();
            if (rawKey.startsWith("!")) continue;

            String[] split = rawKey.split("!", 2);
            String field = split[0];
            String operator = (split.length > 1) ? split[1] : "eq";

            if (value != null && !value.isEmpty()) {
                conditions.add(new Condition(field, operator, value));
            }
        }
    }

    public String buildWhereClause() {
        if (conditions.isEmpty()) return "";
        return conditions.stream()
                .map(cond -> switch (cond.operator()) {
                    case "like" -> cond.field() + " LIKE :" + cond.field();
                    case "ne" -> cond.field() + " <> :" + cond.field();
                    case "gt" -> cond.field() + " > :" + cond.field();
                    case "lt" -> cond.field() + " < :" + cond.field();
                    case "gte" -> cond.field() + " >= :" + cond.field();
                    case "lte" -> cond.field() + " <= :" + cond.field();
                    default -> cond.field() + " = :" + cond.field();
                })
                .collect(Collectors.joining(" AND ", " AND ", ""));
    }

    public void applyParams(Query query) {
        for (Condition cond : conditions) {
            String key = cond.field();
            String value = cond.value();
            if ("like".equals(cond.operator())) {
                query.setParameter(key, "%" + value + "%");
            } else {
                query.setParameter(key, value);
            }
        }
    }
}
