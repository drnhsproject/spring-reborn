package id.co.xinix.spring.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GenerateTimestamp {

    public String generate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.now().format(formatter);
    }
}
