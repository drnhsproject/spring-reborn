package id.co.xinix.auth.modules.user.application.dto;

public record PhotoProfile(
        String bucket,
        String path,
        String mime,
        String filename,
        String originalFilename
) {
}
