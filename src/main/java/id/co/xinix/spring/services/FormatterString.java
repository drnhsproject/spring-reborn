package id.co.xinix.spring.services;

public class FormatterString {

    public String toSnakeCase(String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public String toKebabCase(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase();
    }

    public String toSpacedLowerCase(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase();
    }
}
