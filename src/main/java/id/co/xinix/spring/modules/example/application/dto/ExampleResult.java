package id.co.xinix.spring.modules.example.application.dto;

public record ExampleResult(
    Long id,
    String code,
    String name,
    String nik,
    Integer status
) {
}
