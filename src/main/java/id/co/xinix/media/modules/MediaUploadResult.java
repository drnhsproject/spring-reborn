package id.co.xinix.media.modules;

import java.util.List;

public record MediaUploadResult(
    boolean multiple,
    List<MediaDataResult> results
) {}
