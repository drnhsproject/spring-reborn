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

    public String toSpaced(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("([a-z])([A-Z])", "$1 $2");
    }

    public String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        if (input.matches("^[a-z]+[a-zA-Z0-9]*$")) {
            return input;
        }

        if (input.matches("^[A-Z][a-zA-Z0-9]*$") && !input.contains("_") && !input.contains("-")) {
            return input.substring(0, 1).toLowerCase() + input.substring(1);
        }

        String[] parts = input.split("[-_]");
        StringBuilder camelCase = new StringBuilder(parts[0].toLowerCase());

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                camelCase.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase());
            }
        }

        return camelCase.toString();
    }
}
