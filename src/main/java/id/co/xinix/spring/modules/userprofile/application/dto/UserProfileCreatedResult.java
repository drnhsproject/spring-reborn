package id.co.xinix.spring.modules.userprofile.application.dto;


public record UserProfileCreatedResult(
    Long id,
    String code,
    String firstName,
    String lastName,
    String identityNumber,
    String phoneNumber,
    String photo,
    String address,
    Long userId
) {
}