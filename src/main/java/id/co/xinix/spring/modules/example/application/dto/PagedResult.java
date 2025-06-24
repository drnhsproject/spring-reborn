package id.co.xinix.spring.modules.example.application.dto;

public record PagedResult(
        Long id,
        String code,
        String name,
        String nik,
        Integer age
) {
}
