package id.co.xinix.media.modules;

public record MediaDataResult(
        String bucket,
        String path,
        String mime,
        String filename,
        String originalFilename
) {}
