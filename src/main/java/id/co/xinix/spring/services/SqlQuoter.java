package id.co.xinix.spring.services;

public class SqlQuoter {

    private final String quote;

    public SqlQuoter(String dialect) {
        if (dialect.toLowerCase().contains("postgres")) {
            this.quote = "\"";
        } else if (dialect.toLowerCase().contains("mysql")) {
            this.quote = "`";
        } else {
            this.quote = "";
        }
    }

    public String quote(String identifier) {
        return quote + identifier + quote;
    }
}

