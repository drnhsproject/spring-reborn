package id.co.xinix.spring.services;

public class LikeOperatorResolver {

    private final String operator;

    public LikeOperatorResolver(String databaseProductName) {
        this.operator = databaseProductName.toLowerCase().contains("postgresql") ? "iLIKE" : "LIKE";
    }

    public String get() {
        return operator;
    }
}
