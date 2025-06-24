package id.co.xinix.media.modules;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadMediaHandler {

    private final FileStorage fileStorage;

    public MediaUploadResult handle(String bucket, String path, MultipartFile file, List<MultipartFile> files) {
        List<MultipartFile> resolvedFiles = resolveFiles(file, files);

        List<MediaDataResult> results = fileStorage.upload(bucket, path, resolvedFiles);

        return new MediaUploadResult(results.size() > 1, results);
    }

    private List<MultipartFile> resolveFiles(MultipartFile file, List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) return files;
        if (file != null && !file.isEmpty()) return List.of(file);
        throw new IllegalArgumentException("No file provided");
    }
}
